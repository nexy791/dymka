package com.ribsky.settings.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.settings.ui.library.LibraryActivity

class LibraryNavigationImpl : LibraryNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(Intent(activity, LibraryActivity::class.java))
    }
}
