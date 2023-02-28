package com.ribsky.feed.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.features.*

class FeedNavigationImpl : FeedNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigateProgress(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateProgress()
    }

    override fun navigateLessons(lessonsNavigation: LessonsNavigation, id: String) {
        lessonsNavigation.setup(activity, navController)
        lessonsNavigation.navigateHome(bundleOf(LessonsNavigation.KEY_ID to id))
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome()
    }

    override fun navigateShareWord(shareNavigation: ShareWordNavigation, id: Int) {
        shareNavigation.setup(activity, navController)
        shareNavigation.navigateHome(bundleOf(ShareWordNavigation.KEY_SHARE_WORD to id))
    }

    override fun navigateHome(bundle: Bundle?) {
    }
}
