package com.ribsky.auth.utils.auth.helpers

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.ribsky.auth.utils.auth.helpers.base.AuthHelper
import com.ribsky.common.alias.commonString

class AuthHelperOneTapImpl(
    private val activity: AppCompatActivity,
    private val callback: AuthHelper.AuthCallback,
) : DefaultLifecycleObserver, AuthHelper {

    private var getIntent: ActivityResultLauncher<IntentSenderRequest>? = null

    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initAuth()

        getIntent = activity.activityResultRegistry.register(
            TAG, owner,
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                val credential = oneTapClient!!.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    callback.onSuccess(idToken)
                } else {
                    callback.onError(Throwable("Bad id token"), AuthHelper.AuthType.ONE_TAP)
                }
            } catch (ex: Exception) {
                callback.onError(ex, AuthHelper.AuthType.ONE_TAP)
            }
        }
    }

    override fun auth() {
        callback.onLoading()
        oneTapClient!!.beginSignIn(signInRequest!!)
            .addOnCompleteListener { task ->
                try {
                    getIntent!!.launch(
                        IntentSenderRequest.Builder(task.result.pendingIntent.intentSender).build()
                    )
                } catch (ex: Exception) {
                    callback.onError(ex, AuthHelper.AuthType.ONE_TAP)
                }
            }
    }

    private fun initAuth() {
        oneTapClient = Identity.getSignInClient(activity)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(false)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(commonString.server_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()
    }

    companion object {
        const val TAG = "auth"
    }
}
