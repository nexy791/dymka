package com.ribsky.navigation.features

import android.os.Parcelable
import com.ribsky.common.navigation.Navigation
import kotlinx.parcelize.Parcelize

interface GameNavigation : Navigation {

    fun navigateConfirmDialog(code: String, endPointId: String)

    fun navigateGame(
        endPointId: String,
        testId: String,
        isHost: Boolean,
    )

    companion object {
        const val KEY_LOBBY_STATE: String = "LOBBY_STATE"
        const val KEY_LOBBY_IMAGE: String = "LOBBY_IMAGE"
        const val KEY_LOBBY_ID: String = "LOBBY_ID"

        const val KEY_GAME_END_POINT_ID: String = "end_point_id"
        const val KEY_GAME_TEST_ID: String = "picked_id"
        const val KEY_GAME_USER_STATUS: String = "user_status"

        const val KEY_CONFIRM_CODE = "code"
        const val KEY_CONFIRM_END_POINT_ID = "endPointId"

        const val RESULT_KEY_RESULT_CONFIRM = "TEST_INFO"
        const val RESULT_KEY_RESULT_CONFIRM_ACTION = "TEST_INFO_ACTION"
    }

    @Parcelize
    enum class LobbyState(val status: Int) : Parcelable {
        WAITING(0), SEARCHING(1);

        companion object {
            fun from(status: Int) = values().first { it.status == status }
        }
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

    @Parcelize
    sealed class ConfirmResult(open val endPointId: String) : Parcelable {
        class ACCEPT(override val endPointId: String) : ConfirmResult(endPointId)
        class REJECT(override val endPointId: String) : ConfirmResult(endPointId)
    }
}
