package com.ribsky.navigation.features

import android.os.Parcelable
import androidx.navigation.NavController
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface LessonsNavigation : NavigationWithParams<NavController, LessonsNavigation.Params> {

    @Parcelize
    data class Params(val paragraphId: String) : Base.Params, Parcelable

    companion object {
        const val KEY_ID = "paragraphId"
    }
}
