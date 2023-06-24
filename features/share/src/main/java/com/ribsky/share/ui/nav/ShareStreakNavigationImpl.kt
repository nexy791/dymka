package com.ribsky.share.ui.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.ShareStreakNavigation
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.share.ui.share.ShareStreakActivity

class ShareStreakNavigationImpl: ShareStreakNavigation {
    override fun navigate(navigation: Context, params: ShareStreakNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, ShareStreakActivity::class.java).apply {
                putExtra(ShareStreakNavigation.KEY_SHARE_STREAK, params)
            }
        )
    }
}