package com.ribsky.settings.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.settings.ui.settings.SettingsActivity

class SettingsNavigationImpl : SettingsNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(Intent(activity, SettingsActivity::class.java))
    }

    override fun navigateLoad(loadNavigation: LoaderNavigation) {
        loadNavigation.setup(activity, navController)
        loadNavigation.navigateHome()
    }

    override fun navigateLibrary(libraryNavigation: LibraryNavigation) {
        libraryNavigation.setup(activity, navController)
        libraryNavigation.navigateHome()
    }

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }
}
