package com.ribsky.share.ui.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.ShareStoryNavigation
import com.ribsky.share.ui.story.ShareStoryActivity

class ShareStoryNavigationImpl : ShareStoryNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, ShareStoryActivity::class.java))
    }
}
