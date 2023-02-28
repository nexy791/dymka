package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface LoaderNavigation : Navigation {

    fun navigateAuth(authNavigation: AuthNavigation)

    fun navigateMain(mainNavigation: MainNavigation)
}
