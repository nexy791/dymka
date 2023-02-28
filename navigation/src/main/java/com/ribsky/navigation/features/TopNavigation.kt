package com.ribsky.navigation.features

import android.os.Bundle
import com.ribsky.common.navigation.Navigation

interface TopNavigation : Navigation {

    override fun navigateHome(bundle: Bundle?)

    fun navigateAccount(accountNavigation: AccountNavigation)

    fun navigateProfile(accountNavigation: AccountNavigation, id: Int)
}
