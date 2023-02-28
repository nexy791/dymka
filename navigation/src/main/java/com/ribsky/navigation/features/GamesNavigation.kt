package com.ribsky.navigation.features

import android.os.Parcelable
import com.ribsky.common.navigation.Navigation
import kotlinx.parcelize.Parcelize

interface GamesNavigation : Navigation {

    fun navigateProgress(dialogsNavigation: DialogsNavigation)

    fun navigateShop(shopNavigation: ShopNavigation)

    fun navigatePromptSub(shopNavigation: ShopNavigation)

    fun navigateBeta(betaNavigation: BetaNavigation)

    fun navigateTestInfoDialog(testsNavigation: TestsNavigation, id: String)

    fun navigateLobby(
        gameNavigation: GameNavigation,
        lobbyState: LobbyState,
        image: String,
        id: String,
    )

    @Parcelize
    enum class LobbyState(val status: Int) : Parcelable {
        WAITING(0), SEARCHING(1)
    }
}
