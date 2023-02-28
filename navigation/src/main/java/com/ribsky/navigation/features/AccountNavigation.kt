package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface AccountNavigation : Navigation {

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigateSettings(settingsNavigation: SettingsNavigation)

    fun navigateAuth(authNavigation: AuthNavigation)

    fun navigateBeta(betaNavigation: BetaNavigation)

    fun navigateProfile(id: Int)

    companion object {
        const val KEY_PROFILE_ID = "profileId"
    }
}
