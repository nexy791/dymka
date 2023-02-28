package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface ShopNavigation : Navigation {

    fun navigateLoader(loaderNavigation: LoaderNavigation)

    fun navigateMain(mainNavigation: MainNavigation)

    fun navigatePromptSub()

    companion object {
        const val RESULT_KEY_PROMPT_SUB = "RESULT_KEY_PROMPT_SUB"
    }
}
