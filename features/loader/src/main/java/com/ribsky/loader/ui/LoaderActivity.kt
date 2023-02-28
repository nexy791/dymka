package com.ribsky.loader.ui

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.loader.databinding.ActivityLoaderBinding
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.MainNavigation
import jp.alessandro.android.iab.handler.PurchaseHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoaderActivity :
    BaseActivity<LoaderNavigation, LoaderViewModel, ActivityLoaderBinding>(ActivityLoaderBinding::inflate) {

    override val viewModel: LoaderViewModel by viewModel()
    override val navigation: LoaderNavigation by inject()
    private val authNavigation: AuthNavigation by inject()
    private val mainNavigation: MainNavigation by inject()

    private val purchaseHandler = PurchaseHandler {}
    private val billingClientWrapper: BillingClientWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initBilling()
        getSubInfo()
    }

    private fun initBilling() {
        billingClientWrapper.setup(this, purchaseHandler)
    }

    private fun getSubInfo() {
        billingClientWrapper.getInventory { r ->
            r.fold(
                onSuccess = {
                    viewModel.loadData()
                },
                onFailure = {
                    showError(it.message.orEmpty().ifEmpty { "Помилка!" }) {
                        getSubInfo()
                    }
                }
            )
        }
    }

    override fun initView() {}

    override fun initObs() = with(viewModel) {
        status.observe(this@LoaderActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> navigation.navigateMain(mainNavigation)
                Resource.Status.ERROR -> {
                    when (val ex = it.exception) {
                        is Exceptions.AuthException -> navigation.navigateAuth(authNavigation)
                        else -> showError(ex?.localizedMessage.orEmpty()) {
                            viewModel.loadData()
                        }
                    }
                }
            }
        }
    }

    private fun showError(error: String, callback: () -> Unit) {
        showAlert(
            title = "Помилка",
            message = error.ifBlank { "Невідома помилка" },
            positiveButton = "Повторити",
            positiveAction = callback,
            negativeButton = "Вихід",
            negativeAction = { navigation.navigateAuth(authNavigation) },
        )
    }

    override fun clear() {
        billingClientWrapper.destroy()
    }
}
