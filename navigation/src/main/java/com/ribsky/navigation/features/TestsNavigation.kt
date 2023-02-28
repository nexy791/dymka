package com.ribsky.navigation.features

import com.ribsky.common.navigation.Navigation

interface TestsNavigation : Navigation {

    fun navigateDetails(testNavigation: TestNavigation, testId: String)

    fun navigateTestInfoDialog(testId: String)

    fun navigateProgress(dialogsNavigation: DialogsNavigation)

    fun navigateInternetError(dialogsNavigation: DialogsNavigation)

    fun navigateBeta(betaNavigation: BetaNavigation)

    fun navigateSubPrompt(shopNavigation: ShopNavigation)

    fun navigateShop(shopNavigation: ShopNavigation)

    companion object {
        const val KEY_TEST_INFO_ID = "testId"
        const val RESULT_KEY_TEST_INFO = "TEST_INFO"
        const val RESULT_KEY_TEST_INFO_ID = "TEST_INFO_ID"
    }
}
