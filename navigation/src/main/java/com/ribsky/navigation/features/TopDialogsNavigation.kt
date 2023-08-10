package com.ribsky.navigation.features

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface TopDialogsNavigation : NavigationWithParams<FragmentManager, TopDialogsNavigation.Params> {

    @Parcelize
    enum class Type : Parcelable {
        STARS, TESTS
    }

    @Parcelize
    enum class Status: Parcelable {
        UP, DOWN, MORE
    }

    @Parcelize
    data class UserModel(val name: String, val score: Int, val avatar: String, val hasPrem: Boolean) : Parcelable

    @Parcelize
    data class Params(
        val status: Status,
        val type: Type,
        val users: List<UserModel>,
        val onDismiss: () -> Unit,
    ) : Base.Params, Parcelable

}
