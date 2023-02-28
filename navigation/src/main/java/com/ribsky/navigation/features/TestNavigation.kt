package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface TestNavigation : Navigation {

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigatePromptSub(shopNavigation: ShopNavigation)

    companion object {
        const val KEY_TEST_ID = "testId"
    }
}
