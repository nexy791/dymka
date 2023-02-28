package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface MainNavigation : Navigation {

    fun navigateBeta(betaNavigation: BetaNavigation)

    fun navigateAuth(authNavigation: AuthNavigation)

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigateAccount(accountNavigation: AccountNavigation)
}
