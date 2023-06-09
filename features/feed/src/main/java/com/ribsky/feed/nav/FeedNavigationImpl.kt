package com.ribsky.feed.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.*

class FeedNavigationImpl : FeedNavigation {

    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.nav_feed)
    }
}
