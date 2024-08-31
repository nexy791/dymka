package com.ribsky.games.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.GamesNavigation

class GamesNavigationImpl : GamesNavigation {
    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.nav_games)
    }
}
