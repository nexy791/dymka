package com.ribsky.game.manager.game

import com.google.android.gms.nearby.connection.Payload
import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.game.model.PayLoadModel

class GameManagerImpl(
    private val endPointId: String,
    private val connectionManager: ConnectionManager,
) : GameManager {

    override fun send(payload: PayLoadModel, callback: ConnectionManager.ResultCallback) {
        val json = PayLoadModel.getJsonFromPayLoad(payload)
        val payLoadModel = Payload.fromBytes(json.toByteArray(Charsets.UTF_8))
        connectionManager.send(
            endPointId,
            payLoadModel,
            callback
        )
    }
}
