package com.ribsky.top.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.TopNavigation

class TopNavigationImpl : TopNavigation {
    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
    }

    override fun navigateAccount(accountNavigation: AccountNavigation) {
        accountNavigation.setup(activity, navController)
        accountNavigation.navigateHome()
    }

    override fun navigateProfile(accountNavigation: AccountNavigation, id: Int) {
        accountNavigation.setup(activity, navController)
        accountNavigation.navigateProfile(id)
    }
}
