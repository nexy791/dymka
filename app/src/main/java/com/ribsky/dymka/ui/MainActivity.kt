package com.ribsky.dymka.ui

import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import coil.load
import coil.request.CachePolicy
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.dynamic.DynamicModule
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.party.ProfileBalloonFactory
import com.ribsky.common.utils.update.AppUpdate
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.dynamic.SuccessInstallFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.notification.NotificationPermissionFactory
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.dymka.R
import com.ribsky.dymka.databinding.ActivityMainBinding
import com.ribsky.dymka.dialogs.LoadingDialog
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.BotNavigation
import com.ribsky.navigation.features.IntroNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.navigation.features.TopDialogsNavigation
import com.ribsky.permission.manager.PermissionManager
import com.ribsky.permission.manager.PermissionManagerImpl
import com.ribsky.permission.permissions.NotificationPermissionChecker
import com.skydoves.balloon.balloon
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    private val betaNavigation: BetaNavigation by inject()
    private val authNavigation: AuthNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val introNavigation: IntroNavigation by inject()
    private val botNavigation: BotNavigation by inject()
    private val topDialogsNavigation: TopDialogsNavigation by inject()

    private val accountNavigation: AccountNavigation by inject()

    private var loadingDialog: LoadingDialog? = null

    private val appUpdate: AppUpdate by inject()

    private val activityResult: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private val balloon by balloon<ProfileBalloonFactory>()

    private var permissionManager: PermissionManager? = null

    private val permissionCallback = object : PermissionManager.PermissionCallback {
        override fun onPermissionGranted() {
            Analytics.logEvent(Analytics.Event.NOTIFICATION_PERMISSION_GRANTED)
        }

        override fun onPermissionDenied() {
            Analytics.logEvent(Analytics.Event.NOTIFICATION_PERMISSION_DENIED)
        }
    }


    override fun initView() {
        initToolbar()
        initInAppUpdate()
        initNavigation()
        initCallback()
        checkRateDialog()
        checkBioDialog()
        checkTopDownDialog()
        checkPermission()
    }


    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initInAppUpdate() {
        appUpdate.checkAndPromptUpdate(this)
    }

    private fun initNavigation() = with(binding) {
        setSupportActionBar(toolBar)
        bottomBar.setOnItemSelectedListener {
            if (it.itemId != R.id.nav_bot) {
                navController.navigate(it.itemId)
            } else {
                viewModel.initDynamicModule(this@MainActivity)
            }
            return@setOnItemSelectedListener it.itemId != R.id.nav_bot
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomBar.menu.findItem(
                destination.parent?.id ?: destination.id
            )?.isChecked = true
        }
    }

    private fun initCallback() = with(binding) {
        ivAvatar.setOnClickListener {
            accountNavigation.navigate(navController)
        }

        btnPremium.setOnClickListener {
            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_MENU)
            shopNavigation.navigate(
                this@MainActivity,
                ShopNavigation.Params(Analytics.Event.PREMIUM_FROM_MENU)
            )
        }
    }

    private fun checkRateDialog() {
        if (viewModel.isShouldShowRateDialog) {
            betaNavigation.navigate(this)
        }
    }

    private fun checkBioDialog() {
        if (viewModel.isNeedToFillBio) {
            introNavigation.navigate(this)
        }
    }

    private fun checkTopDownDialog() {
        val random = (0..1).random()
        if (random == 0) {
            checkTopDownTestsDialog {
                if (!it) checkTopDownStarsDialog {}
            }
        } else {
            checkTopDownStarsDialog {
                if (!it) checkTopDownTestsDialog {}
            }
        }
    }

    private fun checkPermission() {
        permissionManager = PermissionManagerImpl(
            this,
            NotificationPermissionChecker()
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (permissionManager?.hasPermissions() == false) {
                showBottomSheetDialog(
                    NotificationPermissionFactory {
                        Analytics.logEvent(Analytics.Event.NOTIFICATION_DIALOG_CLICK)
                        if (permissionManager?.hasBlockedPermissions() == true) {
                            permissionManager?.openAppSettings()
                        } else {
                            permissionManager?.requestPermission(permissionCallback)
                        }
                    }.createDialog()
                )
            }
        }
    }


    private fun checkTopDownTestsDialog(callback: (Boolean) -> Unit) {
        viewModel.isNeedToShowDownTests { rTests ->
            if (rTests.isSuccess) {
                val users = rTests.getOrNull()!!.map {
                    TopDialogsNavigation.UserModel(
                        name = it.name,
                        score = it.score,
                        avatar = it.image,
                        hasPrem = it.hasPrem
                    )
                }
                topDialogsNavigation.navigate(
                    supportFragmentManager,
                    TopDialogsNavigation.Params(
                        TopDialogsNavigation.Status.DOWN,
                        TopDialogsNavigation.Type.TESTS,
                        users
                    ) {}
                )
            }
            callback(rTests.isSuccess)
        }
    }

    private fun checkTopDownStarsDialog(callback: (Boolean) -> Unit) {
        viewModel.isNeedToShowDownStars { rStars ->
            if (rStars.isSuccess) {

                val users = rStars.getOrNull()!!.map {
                    TopDialogsNavigation.UserModel(
                        name = it.name,
                        score = it.starsCount,
                        avatar = it.image,
                        hasPrem = it.hasPrem
                    )
                }

                topDialogsNavigation.navigate(
                    supportFragmentManager,
                    TopDialogsNavigation.Params(
                        TopDialogsNavigation.Status.DOWN,
                        TopDialogsNavigation.Type.STARS,
                        users
                    ) {}
                )
            }
            callback(rStars.isSuccess)
        }
    }


    override fun initObs() = with(viewModel) {
        getProfile()
        getDiscount()
        userStatus.observe(this@MainActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                Resource.Status.ERROR -> {
                    when (result.exception) {
                        is Exceptions.AuthException -> authNavigation.navigate(this@MainActivity)
                        else -> showErrorDialog(result.exception?.localizedMessage) { finish() }
                    }
                }

                Resource.Status.LOADING -> {}
            }
        }
        discountStatus.observe(this@MainActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> result.data?.let {
                    if (!viewModel.isSub) {
                        balloon.showAlignBottom(binding.btnPremium, yOff = 8)
                    }
                }

                else -> {}
            }
        }
        dynamicModuleStatus.observe(this@MainActivity) { result ->
            when (result) {
                DynamicModule.State.DOWNLOADING -> {
                    if (loadingDialog == null) {
                        loadingDialog = LoadingDialog.newInstance()
                        showBottomSheetDialog(loadingDialog!!)
                    }
                }

                DynamicModule.State.INSTALLED -> {
                    if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
                    navigateToBot()
                }

                DynamicModule.State.INSTALL_FINISHED -> {
                    Analytics.logEvent(Analytics.Event.BOT_DOWNLOAD_SUCCESS)
                    if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
                    showBottomSheetDialog(
                        SuccessInstallFactory {
                            navigateToBot()
                        }.createDialog()
                    )
                }

                DynamicModule.State.NONE -> if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
                is DynamicModule.State.REQUIRES_USER_CONFIRMATION -> mDynamicModule.requestUserConfirmation(
                    result.state,
                    activityResult
                )

                is DynamicModule.State.FAILED -> {
                    Analytics.logEvent(Analytics.Event.BOT_DOWNLOAD_ERROR)
                    if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
                    showErrorDialog(result.exception.localizedMessage)
                }
            }
        }
    }

    private fun navigateToBot() {
        botNavigation.navigate(this, BotNavigation.Params()) {
            if (!it) {
                showErrorDialog("Якщо помилка повторюється, спробуй перевстановити застосунок")
            }
        }
    }

    private fun updateUi(user: BaseUserModel) = with(binding) {
        btnPremium.setPremium(viewModel.isSub)
        ivAvatar.load(user.image) {
            crossfade(true)
            placeholder(commonDrawable.cat)
            error(commonDrawable.cat)
            diskCachePolicy(CachePolicy.ENABLED)
            networkCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            showExitAlert(
                positiveAction = { finish() },
                negativeAction = {}
            )
        }
    }

    override fun clear() {
    }
}
