package com.ribsky.auth.utils.auth.helpers

import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.ribsky.auth.utils.auth.helpers.base.AuthHelper
import com.ribsky.common.alias.commonString

class AuthHelperSignInImpl(
    private val registry: ActivityResultRegistry,
    private val activity: AppCompatActivity,
    private val callback: AuthHelper.AuthCallback,
) : DefaultLifecycleObserver, AuthHelper {

    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val launcher =
        registry.register("key", ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)!!
                if (account.idToken != null) {
                    callback.onSuccess(account.idToken!!)
                } else {
                    callback.onError(Throwable("Bad id token"), AuthHelper.AuthType.SIGN_IN)
                }
            } catch (e: ApiException) {
                callback.onError(e, AuthHelper.AuthType.SIGN_IN)
            }
        }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initAuth()
    }

    override fun auth() {
        callback.onLoading()
        val signInIntent = mGoogleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun initAuth() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(commonString.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }
}
