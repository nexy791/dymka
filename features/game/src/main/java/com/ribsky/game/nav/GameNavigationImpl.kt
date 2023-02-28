package com.ribsky.game.nav

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ribsky.game.dialogs.GameConfirmDialog
import com.ribsky.game.ui.game.GameActivity
import com.ribsky.game.ui.lobby.LobbyActivity
import com.ribsky.navigation.features.GameNavigation

class GameNavigationImpl : GameNavigation {
    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
        activity?.startActivity(
            Intent(activity, LobbyActivity::class.java).apply {
                putExtras(bundle!!)
            }
        )
    }

    override fun navigateConfirmDialog(code: String, endPointId: String) {
        GameConfirmDialog.newInstance(code, endPointId)
            .show(activity?.supportFragmentManager!!, GameConfirmDialog.TAG)
    }

    override fun navigateGame(endPointId: String, testId: String, isHost: Boolean) {
        val userStatus = GameNavigation.UserStatus.from(isHost)
        activity?.startActivity(
            Intent(activity, GameActivity::class.java).apply {
                putExtra(GameNavigation.KEY_GAME_END_POINT_ID, endPointId)
                putExtra(GameNavigation.KEY_GAME_TEST_ID, testId)
                putExtra(GameNavigation.KEY_GAME_USER_STATUS, userStatus as Parcelable)
            }
        )
    }
}
