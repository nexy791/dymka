package com.ribsky.account.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.*

class AccountNavigationImpl : AccountNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigateSettings(settingsNavigation: SettingsNavigation) {
        settingsNavigation.setup(activity, navController)
        settingsNavigation.navigateHome()
    }

    override fun navigateAuth(authNavigation: AuthNavigation) {
        authNavigation.setup(activity, navController)
        authNavigation.navigateHome()
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome()
    }

    override fun navigateProfile(id: Int) {
        navController?.navigate(
            navId.profileDialog,
            bundleOf(
                AccountNavigation.KEY_PROFILE_ID to id
            )
        )
    }

    override fun navigateHome(bundle: Bundle?) {
        navController?.navigate(navId.accountDialog)
    }
}
