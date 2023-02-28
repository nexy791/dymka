package com.ribsky.games.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.navigation.features.*

class GamesNavigationImpl : GamesNavigation {

    override fun navigateProgress(dialogsNavigation: DialogsNavigation) {
        dialogsNavigation.setup(activity, navController)
        dialogsNavigation.navigateProgress()
    }

    override fun navigateShop(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigateHome()
    }

    override fun navigatePromptSub(shopNavigation: ShopNavigation) {
        shopNavigation.setup(activity, navController)
        shopNavigation.navigatePromptSub()
    }

    override fun navigateBeta(betaNavigation: BetaNavigation) {
        betaNavigation.setup(activity, navController)
        betaNavigation.navigateHome()
    }

    override fun navigateTestInfoDialog(testsNavigation: TestsNavigation, id: String) {
        testsNavigation.setup(activity, navController)
        testsNavigation.navigateTestInfoDialog(id)
    }

    override fun navigateLobby(
        gameNavigation: GameNavigation,
        lobbyState: GamesNavigation.LobbyState,
        image: String,
        id: String,
    ) {
        val lobbyGame = GameNavigation.LobbyState.from(lobbyState.status)
        gameNavigation.setup(activity, navController)
        gameNavigation.navigateHome(
            bundleOf(
                GameNavigation.KEY_LOBBY_STATE to lobbyGame,
                GameNavigation.KEY_LOBBY_IMAGE to image,
                GameNavigation.KEY_LOBBY_ID to id
            )
        )
    }

    override var activity: AppCompatActivity? = null
    override var navController: NavController? = null

    override fun navigateHome(bundle: Bundle?) {
    }
}
