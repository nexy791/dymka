package com.ribsky.navigation.features

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithResult
import com.ribsky.navigation.base.NavigationWithResultAndParams
import kotlinx.parcelize.Parcelize

interface PayWallNavigation : NavigationWithResultAndParams<FragmentManager, PayWallNavigation.Params, PayWallNavigation.Callback> {

    @Parcelize
    data class Params(val date: String) : Base.Params, Parcelable

    interface Callback {
        fun onDiscount()
    }

}