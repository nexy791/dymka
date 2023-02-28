package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface SettingsNavigation : Navigation {

    fun navigateLoad(loadNavigation: LoaderNavigation)

    fun navigateLibrary(libraryNavigation: LibraryNavigation)

    fun navigateShop(shopNavigation: ShopNavigation)
}
