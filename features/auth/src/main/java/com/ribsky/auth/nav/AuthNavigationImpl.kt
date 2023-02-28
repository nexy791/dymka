package com.ribsky.auth.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.auth.ui.AuthActivity
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.LoaderNavigation

class AuthNavigationImpl : AuthNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateLoader(loaderNavigation: LoaderNavigation) {
        loaderNavigation.setup(activity, navController)
        loaderNavigation.navigateHome()
    }

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, AuthActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}
