package com.ribsky.share.ui.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.share.ui.word.ShareWordActivity

class ShareWordNavigationImpl : ShareWordNavigation {

    override fun navigate(navigation: Context, params: ShareWordNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, ShareWordActivity::class.java).apply {
                putExtra(ShareWordNavigation.KEY_SHARE_WORD, params.id)
            }
        )
    }
}
