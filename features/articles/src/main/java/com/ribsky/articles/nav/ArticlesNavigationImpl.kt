package com.ribsky.articles.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.ArticlesNavigation

class ArticlesNavigationImpl : ArticlesNavigation {
    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.articleFragment)
    }
}
