package com.ribsky.auth.nav

import android.content.Context
import android.content.Intent
import com.ribsky.auth.ui.AuthActivity
import com.ribsky.navigation.features.AuthNavigation

class AuthNavigationImpl : AuthNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(
            Intent(navigation, AuthActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}
