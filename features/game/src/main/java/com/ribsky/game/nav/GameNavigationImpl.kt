package com.ribsky.game.nav

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.ribsky.game.ui.game.GameActivity
import com.ribsky.navigation.features.GameNavigation

class GameNavigationImpl : GameNavigation {

    override fun navigate(navigation: Context, params: GameNavigation.Params) {
        val userStatus = GameNavigation.UserStatus.from(params.isHost)
        navigation.startActivity(
            Intent(navigation, GameActivity::class.java).apply {
                putExtra(GameNavigation.KEY_GAME_END_POINT_ID, params.endPointId)
                putExtra(GameNavigation.KEY_GAME_TEST_ID, params.testId)
                putExtra(GameNavigation.KEY_GAME_USER_STATUS, userStatus as Parcelable)
            }
        )
    }
}
