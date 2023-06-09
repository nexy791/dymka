package com.ribsky.account.nav

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.ProfileNavigation

class ProfileNavigationImpl : ProfileNavigation {

    override fun navigate(navigation: NavController, params: ProfileNavigation.Params) {
        navigation.navigate(navId.profileDialog, bundleOf(ProfileNavigation.KEY_ID to params.id))
    }
}
