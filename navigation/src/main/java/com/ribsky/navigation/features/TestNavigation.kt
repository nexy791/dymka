package com.ribsky.navigation.features

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import com.ribsky.navigation.base.NavigationWithResultAndParams
import kotlinx.parcelize.Parcelize

interface TestNavigation : NavigationWithResultAndParams<Context, TestNavigation.Params, ActivityResultLauncher<Intent>> {

    @Parcelize
    data class Params(
        val testId: String,
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_TEST_ID = "testId"
        const val KEY_TEST_RESULT = "testResult"
    }
}
