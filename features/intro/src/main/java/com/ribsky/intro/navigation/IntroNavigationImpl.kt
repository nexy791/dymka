package com.ribsky.intro.navigation

import android.content.Context
import android.content.Intent
import com.ribsky.intro.ui.IntroActivity
import com.ribsky.navigation.features.IntroNavigation

class IntroNavigationImpl : IntroNavigation {
    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, IntroActivity::class.java))
    }
}