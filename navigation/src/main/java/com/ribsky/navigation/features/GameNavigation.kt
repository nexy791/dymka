package com.ribsky.navigation.features

import android.content.Context
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithParams
import kotlinx.parcelize.Parcelize

interface GameNavigation : NavigationWithParams<Context, GameNavigation.Params> {

    @Parcelize
    data class Params(
        val endPointId: String,
        val testId: String,
        val isHost: Boolean,
    ) : Base.Params, Parcelable

    companion object {
        const val KEY_LOBBY_STATE: String = "LOBBY_STATE"
        const val KEY_LOBBY_IMAGE: String = "LOBBY_IMAGE"
        const val KEY_LOBBY_ID: String = "LOBBY_ID"

        const val KEY_GAME_END_POINT_ID: String = "end_point_id"
        const val KEY_GAME_TEST_ID: String = "picked_id"
        const val KEY_GAME_USER_STATUS: String = "user_status"
    }

    @Parcelize
    enum class UserStatus(private val isHost: Boolean) : Parcelable {
        HOST(true), GUEST(false);

        fun isHost() = isHost
        fun isGuest() = !isHost

        companion object {
            fun from(isHost: Boolean) = if (isHost) HOST else GUEST
        }
    }
}
