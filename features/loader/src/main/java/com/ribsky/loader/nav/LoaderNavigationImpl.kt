package com.ribsky.loader.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.loader.ui.LoaderActivity
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.MainNavigation

class LoaderNavigationImpl : LoaderNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateAuth(authNavigation: AuthNavigation) {
        authNavigation.setup(activity, navController)
        authNavigation.navigateHome()
    }

    override fun navigateMain(mainNavigation: MainNavigation) {
        mainNavigation.setup(activity, navController)
        mainNavigation.navigateHome()
    }

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, LoaderActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}
