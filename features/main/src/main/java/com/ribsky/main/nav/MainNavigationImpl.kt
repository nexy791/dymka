package com.ribsky.main.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.main.ui.MainActivity
import com.ribsky.navigation.features.*

class MainNavigationImpl : MainNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome()
    }

    override fun navigateAuth(authNavigation: AuthNavigation) {
        authNavigation.setup(activity, navController)
        authNavigation.navigateHome()
    }

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigateAccount(accountNavigation: AccountNavigation) {
        accountNavigation.setup(activity, navController)
        accountNavigation.navigateHome()
    }
}
