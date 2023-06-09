package com.ribsky.test.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.test.ui.TestDetailsActivity

class TestNavigationImpl : TestNavigation {

    override fun navigate(navigation: Context, params: TestNavigation.Params) {
        navigation.startActivity(
            Intent(navigation, TestDetailsActivity::class.java).apply {
                putExtra(TestNavigation.KEY_TEST_ID, params.testId)
            }
        )
    }
}
