package com.ribsky.share.ui.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.share.ui.message.ShareMessageActivity

class ShareMessageNavigationImpl : ShareMessageNavigation {

    override fun navigate(navigation: Context, params: ShareMessageNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, ShareMessageActivity::class.java).apply {
                putExtra(ShareMessageNavigation.KEY_SHARE_MESSAGE_ID, params.share)
            }
        )
    }
}
