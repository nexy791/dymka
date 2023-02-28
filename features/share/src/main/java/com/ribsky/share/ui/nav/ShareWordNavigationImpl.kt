package com.ribsky.share.ui.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.share.ui.word.ShareWordActivity

class ShareWordNavigationImpl : ShareWordNavigation {
    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, ShareWordActivity::class.java).apply {
                putExtra(ShareWordNavigation.KEY_SHARE_WORD, bundle)
            }
        )
    }
}
