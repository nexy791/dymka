package com.ribsky.main.ui

import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.request.CachePolicy
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.AlertsExt.Companion.showErrorAlert
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.snackbar
import com.ribsky.common.utils.update.AppUpdate
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.main.R
import com.ribsky.main.databinding.ActivityMainBinding
import com.ribsky.main.utils.HintHelper
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<MainNavigation, MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    override val navigation: MainNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val authNavigation: AuthNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val accountNavigation: AccountNavigation by inject()

    private val appUpdate: AppUpdate by inject()

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
        navigation.navController!!.apply {
            bottomBar.setupWithNavController(this)
            val appBarConfiguration =
                AppBarConfiguration(
                    setOf(
                        navId.nav_feed,
                        navId.nav_games,
                        navId.nav_tests,
                        navId.nav_top
                    )
                )
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    private fun initCallback() = with(binding) {
        ivAvatar.setOnClickListener {
            navigation.navigateAccount(accountNavigation)
        }

        btnPremium.setOnClickListener {
            navigation.navigateShop(shopNavigation)
        }
    }

    private fun checkRateDialog() {
        if (viewModel.isShouldShowRateDialog) {
            navigation.navigateBeta(betaNavigation)
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
                        is Exceptions.AuthException -> navigation.navigateAuth(authNavigation)
                        else -> showErrorAlert(
                            message = result.exception?.localizedMessage,
                            positiveAction = { getProfile() },
                            negativeAction = { navigation.navigateAuth(authNavigation) }
                        )
                    }
                }
                else -> {}
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
        navigation.navController!!.navigateUp() || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (!navigation.navController!!.navigateUp()) {
            showExitAlert(
                positiveAction = { finish() },
                negativeAction = {}
            )
        }
    }

    override fun clear() {
    }
}
