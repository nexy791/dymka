package com.ribsky.game.manager.game

import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.game.model.PayLoadModel

interface GameManager {

    fun send(payload: PayLoadModel, callback: ConnectionManager.ResultCallback)
}
