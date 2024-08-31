package com.ribsky.auth.ui

import android.text.method.LinkMovementMethod
import androidx.core.text.parseAsHtml
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.auth.databinding.ActivityAuthBinding
import com.ribsky.auth.dialogs.DialogSign
import com.ribsky.auth.utils.auth.helpers.AuthHelperCreditsImpl
import com.ribsky.auth.utils.auth.helpers.AuthHelperOneTapImpl
import com.ribsky.auth.utils.auth.helpers.AuthHelperSignInImpl
import com.ribsky.auth.utils.auth.helpers.base.AuthHelper
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.navigation.features.LoaderNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity :
    BaseActivity<AuthViewModel, ActivityAuthBinding>(ActivityAuthBinding::inflate),
    AuthHelper.AuthCallback {

    override val viewModel: AuthViewModel by viewModel()
    private val loaderNavigation: LoaderNavigation by inject()

    private var authHelperOneTap: AuthHelper? = null
    private var authHelperSignIn: AuthHelper? = null
    private var authHelperCredentials: AuthHelper? = null

    private var state: LoadingStateDelegate? = null

    override fun initView() {
        Analytics.logEvent(Analytics.Event.OPEN_AUTH)
        clear()
        initState()
        initTexts()
        initAuth()
        initBtns()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(btnAuth, progressBar).apply {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            showContent()
        }
    }

    private fun initTexts() = with(binding.tvPrivacy) {
        movementMethod = LinkMovementMethod.getInstance()
        text = """
            Продовжуючи, я погоджуюсь з <a href="https://dymka.me/rules.html">правилами</a><br>та <a href="https://dymka.me/privacy.html">політикою конфіденційності</a>
        """.trimIndent().parseAsHtml()
    }

    private fun initBtns() = with(binding) {
        btnAuth.setOnClickListener {
            showBottomSheetDialog(DialogSign.newInstance {
                when (it) {
                    DialogSign.Type.GOOGLE -> authHelperCredentials!!.auth()
                }
            })
        }
    }

    private fun initAuth() {
        authHelperOneTap = AuthHelperOneTapImpl(
            activity = this,
            callback = this,
        ).apply {
            lifecycle.addObserver(this)
        }

        authHelperSignIn = AuthHelperSignInImpl(
            registry = activityResultRegistry,
            activity = this,
            callback = this,
        ).apply {
            lifecycle.addObserver(this)
        }

        authHelperCredentials = AuthHelperCreditsImpl(
            activity = this,
            callback = this,
        ).apply {
            lifecycle.addObserver(this)
        }
    }

    override fun initObs() = with(viewModel) {
        authStatus.observe(this@AuthActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state!!.showLoading()
                }

                Resource.Status.SUCCESS -> {
                    Analytics.logEvent(Analytics.Event.PASS_AUTH)
                    loaderNavigation.navigate(this@AuthActivity)
                }

                Resource.Status.ERROR -> {
                    Analytics.logEvent(Analytics.Event.ERROR_AUTH_SERVER)
                    showErrorDialog(result.exception?.localizedMessage)
                }
            }
        }
    }

    override fun onSuccess(idToken: String) {
        viewModel.authUser(idToken)
    }

    override fun onLoading() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state!!.showLoading()
    }

    override fun onError(error: Throwable, type: AuthHelper.AuthType) {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state!!.showContent()

        when (type) {
            AuthHelper.AuthType.CREDENTIALS -> {
                Analytics.logEvent(Analytics.Event.ERROR_AUTH_CREDS)
                showAlertError(
                    success = { authHelperOneTap!!.auth() },
                    error = { showErrorDialog(error.localizedMessage) }
                )
            }
            AuthHelper.AuthType.ONE_TAP -> {
                Analytics.logEvent(Analytics.Event.ERROR_AUTH_ONE_TAP)
                showAlertError(
                    success = { authHelperSignIn!!.auth() },
                    error = { showErrorDialog(error.localizedMessage) }
                )
            }
            AuthHelper.AuthType.SIGN_IN -> {
                Analytics.logEvent(Analytics.Event.ERROR_AUTH_SIGN_IN)
                showErrorDialog(error.localizedMessage)
            }
        }
    }

    private fun showAlertError(
        success: () -> Unit,
        error: () -> Unit
    ) {
        showAlert(
            title = "Помилка",
            message = "Помилка авторизації, давай спробуємо ще раз? \uD83D\uDC31",
            cancelable = false,
            positiveButton = "Повторити",
            positiveAction = success,
            negativeButton = "Вийти",
            negativeAction = error
        )
    }

    override fun clear() {
        viewModel.clear()
        authHelperSignIn = null
        authHelperOneTap = null
        authHelperCredentials = null
        state = null
    }

}
