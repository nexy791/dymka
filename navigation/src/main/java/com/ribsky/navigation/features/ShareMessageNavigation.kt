package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface ShareMessageNavigation : NavigationWithParams<Context, ShareMessageNavigation.Params> {

    @Parcelize
    data class Params(
        val share: String,
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_SHARE_MESSAGE_ID = "share_key_id"
    }
}
