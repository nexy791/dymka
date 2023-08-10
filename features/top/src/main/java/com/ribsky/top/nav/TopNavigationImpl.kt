package com.ribsky.top.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.TopNavigation

class TopNavigationImpl : TopNavigation {
    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.nav_top)
    }
}
