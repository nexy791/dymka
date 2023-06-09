package com.ribsky.loader.nav

import android.content.Context
import android.content.Intent
import com.ribsky.loader.ui.LoaderActivity
import com.ribsky.navigation.features.LoaderNavigation

class LoaderNavigationImpl : LoaderNavigation {
    override fun navigate(navigation: Context) {
        navigation.startActivity(
            Intent(navigation, LoaderActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}
