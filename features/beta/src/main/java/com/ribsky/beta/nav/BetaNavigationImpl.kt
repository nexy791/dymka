package com.ribsky.beta.nav

import android.content.Context
import android.content.Intent
import com.ribsky.beta.ui.BetaActivity
import com.ribsky.navigation.features.BetaNavigation

class BetaNavigationImpl : BetaNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, BetaActivity::class.java))
    }
}
