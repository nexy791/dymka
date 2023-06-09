package com.ribsky.account.nav

import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.AccountNavigation

class AccountNavigationImpl : AccountNavigation {
    override fun navigate(navigation: NavController) {
        navigation.navigate(navId.accountDialog)
    }
}
