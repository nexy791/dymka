package com.ribsky.auth.utils.auth.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.ribsky.auth.utils.auth.helpers.base.AuthHelper
import com.ribsky.common.alias.commonString
import kotlinx.coroutines.launch

class AuthHelperCreditsImpl(
    private val activity: AppCompatActivity,
    private val callback: AuthHelper.AuthCallback,
) : DefaultLifecycleObserver, AuthHelper {

    private val credentialManager = CredentialManager.create(activity)

    private var request: GetCredentialRequest? = null


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initAuth()
    }

    override fun auth() {
        callback.onLoading()
        activity.lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request!!,
                    context = activity,
                )
                handleSignIn(result)
            } catch (t: Throwable) {
                callback.onError(t, AuthHelper.AuthType.CREDENTIALS)
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        callback.onSuccess(idToken)
                    } catch (t: Throwable) {
                        callback.onError(t, AuthHelper.AuthType.CREDENTIALS)
                    }
                } else  {
                    callback.onError(
                        Throwable("Unexpected type of credential"),
                        AuthHelper.AuthType.CREDENTIALS
                    )
                }
            }

            else -> {
                callback.onError(
                    Throwable("Unexpected type of credential"),
                    AuthHelper.AuthType.CREDENTIALS
                )
            }
        }
    }

    private fun initAuth() {
        val googleIdOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(activity.getString(commonString.server_client_id))
            .build()

        request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}
