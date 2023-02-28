package com.ribsky.shop.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.MainNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.shop.dialogs.prompt.SubPromptDialog
import com.ribsky.shop.ui.ShopActivity

class ShopNavigationImpl : ShopNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(Intent(activity, ShopActivity::class.java))
    }

    override fun navigateLoader(loaderNavigation: LoaderNavigation) {
        loaderNavigation.setup(activity, navController)
        loaderNavigation.navigateHome()
    }

    override fun navigateMain(mainNavigation: MainNavigation) {
        mainNavigation.setup(activity, navController)
        mainNavigation.navigateHome()
    }

    override fun navigatePromptSub() {
        if (navController != null) {
            navController?.navigate(navId.subPromptDialog)
        } else {
            SubPromptDialog.newInstance()
                .show(activity?.supportFragmentManager!!, SubPromptDialog.TAG)
        }
    }
}
