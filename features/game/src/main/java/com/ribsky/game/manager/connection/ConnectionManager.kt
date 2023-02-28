package com.ribsky.game.manager.connection

import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

interface ConnectionManager {

    interface ConnectionPayLoadListener {
        fun onPayLoadReceived(payload: Payload)
        fun onPayloadTransferUpdate(update: PayloadTransferUpdate)
    }

    interface ConnectionListener {
        fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo)
        fun onConnectionResult(endpointId: String, result: ConnectionResolution)
        fun onDisconnected(endpointId: String)
    }

    interface DiscoveryListener {
        fun onEndpointFound(endpointId: String)
        fun onEndpointLost(endpointId: String)
    }

    interface ResultCallback {
        fun onSuccess()
        fun onFailure(error: Exception)
    }

    fun setConnectionPayLoadListener(listener: ConnectionPayLoadListener)

    fun setConnectionListener(listener: ConnectionListener)

    fun setDiscoveryListener(listener: DiscoveryListener)

    fun setConnectionResultCallback(callback: ResultCallback)

    fun requestConnection(endpointId: String, callback: ResultCallback)

    fun acceptConnection(endpointId: String)

    fun rejectConnection(endpointId: String)

    fun startAdvertising(callback: ResultCallback)

    fun stopAdvertising()

    fun startDiscovery(callback: ResultCallback)

    fun stopDiscovery()

    fun send(endpointId: String, payload: Payload, callback: ResultCallback)

    fun disconnect(endpointId: String)

    fun stop()
}
