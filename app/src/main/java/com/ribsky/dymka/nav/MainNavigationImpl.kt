package com.ribsky.dymka.nav

import android.content.Context
import android.content.Intent
import com.ribsky.dymka.ui.MainActivity
import com.ribsky.navigation.features.MainNavigation

class MainNavigationImpl : MainNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(
            Intent(navigation, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}
