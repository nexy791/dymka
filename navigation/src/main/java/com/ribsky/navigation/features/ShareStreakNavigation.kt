package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface ShareStreakNavigation : NavigationWithParams<Context, ShareStreakNavigation.Params> {

    @Parcelize
    data class Params(
        val count: Int,
        val isDone: Boolean
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_SHARE_STREAK = "share_key"
    }
}