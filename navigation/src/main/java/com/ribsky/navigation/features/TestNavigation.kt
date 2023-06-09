package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface TestNavigation : NavigationWithParams<Context, TestNavigation.Params> {

    @Parcelize
    data class Params(
        val testId: String,
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_TEST_ID = "testId"
    }
}
