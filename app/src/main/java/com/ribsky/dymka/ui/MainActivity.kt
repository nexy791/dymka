package com.ribsky.dymka.ui

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import coil.load
import coil.request.CachePolicy
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.dynamic.DynamicModule
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.ext.ViewExt.Companion.snackbar
import com.ribsky.common.utils.update.AppUpdate
import com.ribsky.dialogs.factory.dynamic.SuccessInstallFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.dymka.R
import com.ribsky.dymka.databinding.ActivityMainBinding
import com.ribsky.dymka.dialogs.LoadingDialog
import com.ribsky.dymka.utils.HintHelper
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    private val betaNavigation: BetaNavigation by inject()
    private val authNavigation: AuthNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()

    private val accountNavigation: AccountNavigation by inject()

    private var loadingDialog: LoadingDialog? = null

    private val appUpdate: AppUpdate by inject()

    private val activityResultRegistry =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun initView() {
        initToolbar()
        initInAppUpdate()
        initNavigation()
        initCallback()
        initHint()
        checkRateDialog()
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
                loadingDialog = LoadingDialog.newInstance()
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
            shopNavigation.navigate(this@MainActivity)
        }
    }

    private fun checkRateDialog() {
        if (viewModel.isShouldShowRateDialog) {
            betaNavigation.navigate(this)
        }
    }

    private fun initHint() = with(binding) {
        snackbar(HintHelper.randomHint).apply {
            anchorView = bottomBar
            show()
        }
    }

    override fun initObs() = with(viewModel) {
        getProfile()
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
        dynamicModuleStatus.observe(this@MainActivity) { result ->
            when (result) {
                DynamicModule.State.DOWNLOADING -> {
                    if (loadingDialog == null) loadingDialog = LoadingDialog.newInstance()
                    if (!loadingDialog!!.isAdded) {
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
                    activityResultRegistry
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
        startActivity(
            Intent(
                this@MainActivity,
                Class.forName("com.ribsky.bot.ui.BotActivity")
            )
        )
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
