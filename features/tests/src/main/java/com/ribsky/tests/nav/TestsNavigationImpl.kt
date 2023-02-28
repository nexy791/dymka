package com.ribsky.tests.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.alias.navId
import com.ribsky.navigation.features.*
import com.ribsky.navigation.features.TestsNavigation.Companion.KEY_TEST_INFO_ID

class TestsNavigationImpl : TestsNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateDetails(testNavigation: TestNavigation, testId: String) {
        testNavigation.setup(activity, navController)
        testNavigation.navigateHome(bundleOf(TestNavigation.KEY_TEST_ID to testId))
    }

    override fun navigateTestInfoDialog(testId: String) {
        navController?.navigate(navId.testInfoDialog, bundleOf(KEY_TEST_INFO_ID to testId))
    }

    override fun navigateProgress(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateProgress()
    }

    override fun navigateInternetError(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateCheckConnection()
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome()
    }

    override fun navigateSubPrompt(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigatePromptSub()
    }

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigateHome(bundle: Bundle?) {}
}
