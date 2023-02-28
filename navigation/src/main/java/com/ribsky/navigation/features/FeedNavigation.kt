package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface FeedNavigation : Navigation {

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigateProgress(dialogsNavigation: DialogsNavigation)

    fun navigateLessons(lessonsNavigation: LessonsNavigation, id: String)

    fun navigateBeta(betaNavigation: BetaNavigation)

    fun navigateShareWord(shareNavigation: ShareWordNavigation, id: Int)
}
