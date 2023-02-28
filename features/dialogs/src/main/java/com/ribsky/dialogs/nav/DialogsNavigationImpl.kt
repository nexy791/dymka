package com.ribsky.dialogs.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.DialogsNavigation

class DialogsNavigationImpl : DialogsNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateProgress() {
        navController?.navigate(navId.progressDialog)
    }

    override fun navigateCheckConnection() {
        navController?.navigate(navId.checkConnectionDialog)
    }

    override fun navigateHome(bundle: Bundle?) {}
}
