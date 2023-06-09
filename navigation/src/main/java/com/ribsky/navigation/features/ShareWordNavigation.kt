package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface ShareWordNavigation : NavigationWithParams<Context, ShareWordNavigation.Params> {

    @Parcelize
    data class Params(
        val id: Int,
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_SHARE_WORD = "share_key"
    }
}
