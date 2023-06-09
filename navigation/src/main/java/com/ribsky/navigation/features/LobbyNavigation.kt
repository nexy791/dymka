package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface LobbyNavigation : NavigationWithParams<Context, LobbyNavigation.LobbyInfo> {

    @Parcelize
    data class LobbyInfo(
        val state: LobbyState,
        val image: String,
        val id: String,
    ) : Base.Params, Parcelable

    @Parcelize
    enum class LobbyState(val status: Int) : Parcelable {
        WAITING(0), SEARCHING(1);

        companion object {
            fun from(status: Int) = values().first { it.status == status }
        }
    }
}
