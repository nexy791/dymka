package com.ribsky.share.ui.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.share.ui.message.ShareMessageActivity

class ShareMessageNavigationImpl : ShareMessageNavigation {
    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, ShareMessageActivity::class.java).apply {
                putExtra(ShareMessageNavigation.KEY_SHARE_MESSAGE, bundle)
            }
        )
    }
}
