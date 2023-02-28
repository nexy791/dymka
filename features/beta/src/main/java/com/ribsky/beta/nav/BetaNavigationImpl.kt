package com.ribsky.beta.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.beta.ui.BetaActivity
import com.ribsky.navigation.features.BetaNavigation

class BetaNavigationImpl : BetaNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(Intent(activity, BetaActivity::class.java))
    }
}
