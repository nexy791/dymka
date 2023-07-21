package com.ribsky.auth.utils.auth.helpers.base

interface AuthHelper {

    enum class AuthType {
        ONE_TAP,
        SIGN_IN,
    }

    interface AuthCallback {
        fun onSuccess(idToken: String)
        fun onLoading()
        fun onError(error: Throwable, type: AuthType)
    }

    fun auth()
}
