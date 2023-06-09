package com.ribsky.game.nav

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.ribsky.game.ui.lobby.LobbyActivity
import com.ribsky.navigation.features.GameNavigation
import com.ribsky.navigation.features.LobbyNavigation

class LobbyNavigationImpl : LobbyNavigation {
    override fun navigate(navigation: Context, params: LobbyNavigation.LobbyInfo) {
        navigation.startActivity(
            Intent(navigation, LobbyActivity::class.java).apply {
                putExtra(GameNavigation.KEY_LOBBY_STATE, params.state as Parcelable)
                putExtra(GameNavigation.KEY_LOBBY_IMAGE, params.image)
                putExtra(GameNavigation.KEY_LOBBY_ID, params.id)
            }
        )
    }
}
