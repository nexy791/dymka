package com.ribsky.settings.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.settings.ui.settings.SettingsActivity

class SettingsNavigationImpl : SettingsNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, SettingsActivity::class.java))
    }
}
