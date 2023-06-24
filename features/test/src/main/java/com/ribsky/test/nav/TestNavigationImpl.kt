package com.ribsky.test.nav

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.test.ui.TestDetailsActivity

class TestNavigationImpl : TestNavigation {

    override fun navigate(
        navigation: Context,
        params: TestNavigation.Params,
        callback: ActivityResultLauncher<Intent>,
    ) {
        callback.launch(
            Intent(navigation, TestDetailsActivity::class.java).apply {
                putExtra(TestNavigation.KEY_TEST_ID, params.testId)
            }
        )
    }
}
