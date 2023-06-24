package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.analytics.Analytics
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface ShopNavigation : NavigationWithParams<Context, ShopNavigation.Params> {

    @Parcelize
    data class Params(
        val param: Analytics.Event,
    ) : Base.Params, Parcelable

    companion object {
        const val PARAM = "param"
    }

}
