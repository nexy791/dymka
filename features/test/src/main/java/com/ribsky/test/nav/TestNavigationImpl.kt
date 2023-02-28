package com.ribsky.test.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.test.ui.TestDetailsActivity

class TestNavigationImpl : TestNavigation {

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigatePromptSub(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigatePromptSub()
    }

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, TestDetailsActivity::class.java).apply {
                putExtras(bundle!!)
            }
        )
    }
}
