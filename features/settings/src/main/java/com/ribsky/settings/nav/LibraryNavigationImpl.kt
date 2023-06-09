package com.ribsky.settings.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.settings.ui.library.LibraryActivity

class LibraryNavigationImpl : LibraryNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, LibraryActivity::class.java))
    }
}
