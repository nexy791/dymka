package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface AuthNavigation : Navigation {

    fun navigateLoader(loaderNavigation: LoaderNavigation)
}
