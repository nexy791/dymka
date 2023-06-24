package com.ribsky.auth.utils.auth.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.ribsky.auth.utils.auth.helpers.base.AuthHelper


class AuthHelperFaceBookImpl(
    private val activity: AppCompatActivity,
    private val callback: AuthHelper.AuthCallback,
) : DefaultLifecycleObserver, AuthHelper {

    private var mCallbackManager: CallbackManager? = null
    val callbackManager: CallbackManager get() = mCallbackManager!!

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initAuth()
    }

    private fun initAuth() {
        FacebookSdk.sdkInitialize(activity.applicationContext)
        mCallbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    callback.onSuccess(result.accessToken.token)
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                    callback.onError(error, AuthHelper.AuthType.FACEBOOK)
                }
            })
    }

    override fun auth() {
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

}