package com.ribsky.tests.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.TestsNavigation

class TestsNavigationImpl : TestsNavigation {
    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.nav_tests)
    }
}
