package com.ribsky.game.manager.connection

import android.content.Context
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class ConnectionManagerImpl(private val context: Context) : ConnectionManager {

    private var connectionsClient: ConnectionsClient? = null
    private val name: String = "Dymka"

    private var callback: ConnectionManager.ConnectionPayLoadListener? = null
    private var callbackConnection: ConnectionManager.ConnectionListener? = null
    private var callbackDiscovery: ConnectionManager.DiscoveryListener? = null
    private var callbackResult: ConnectionManager.ResultCallback? = null

    private val connectionPayloadListener: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            callback?.onPayLoadReceived(payload)
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            callback?.onPayloadTransferUpdate(update)
        }
    }

    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                callbackConnection?.onConnectionInitiated(endpointId, connectionInfo)
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                callbackConnection?.onConnectionResult(endpointId, result)
            }

            override fun onDisconnected(endpointId: String) {
                callbackConnection?.onDisconnected(endpointId)
            }
        }

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                callbackDiscovery?.onEndpointFound(endpointId)
            }

            override fun onEndpointLost(endpointId: String) {
                callbackDiscovery?.onEndpointLost(endpointId)
            }
        }

    override fun setConnectionPayLoadListener(listener: ConnectionManager.ConnectionPayLoadListener) {
        callback = listener
    }

    override fun setConnectionListener(listener: ConnectionManager.ConnectionListener) {
        callbackConnection = listener
    }

    override fun setDiscoveryListener(listener: ConnectionManager.DiscoveryListener) {
        callbackDiscovery = listener
    }

    override fun setConnectionResultCallback(callback: ConnectionManager.ResultCallback) {
        callbackResult = callback
    }

    override fun requestConnection(
        endpointId: String,
        callback: ConnectionManager.ResultCallback,
    ) {
        connectionsClient()
            .requestConnection(
                name,
                endpointId,
                connectionLifecycleCallback
            )
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener {
                callback.onFailure(it)
            }
    }

    override fun acceptConnection(endpointId: String) {
        connectionsClient().acceptConnection(endpointId, connectionPayloadListener)
    }

    override fun rejectConnection(endpointId: String) {
        connectionsClient().rejectConnection(endpointId)
    }

    override fun startAdvertising(callback: ConnectionManager.ResultCallback) {
        connectionsClient().startAdvertising(
            name, context.packageName, connectionLifecycleCallback,
            AdvertisingOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build()
        ).addOnSuccessListener {
            callback.onSuccess()
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    override fun stopAdvertising() {
        connectionsClient().stopAdvertising()
    }

    override fun startDiscovery(callback: ConnectionManager.ResultCallback) {
        connectionsClient().startDiscovery(
            context.packageName,
            endpointDiscoveryCallback,
            DiscoveryOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build()
        ).addOnSuccessListener {
            callback.onSuccess()
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    override fun stopDiscovery() {
        connectionsClient().stopDiscovery()
    }

    override fun send(
        endpointId: String,
        payload: Payload,
        callback: ConnectionManager.ResultCallback,
    ) {
        connectionsClient().sendPayload(endpointId, payload)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener {
                callback.onFailure(it)
            }
    }

    override fun disconnect(endpointId: String) {
        connectionsClient().disconnectFromEndpoint(endpointId)
    }

    override fun stop() {
        connectionsClient?.stopDiscovery()
        connectionsClient?.stopAdvertising()
        connectionsClient?.stopAllEndpoints()
        connectionsClient = null
    }

    private fun connectionsClient(): ConnectionsClient {
        if (connectionsClient == null) {
            connectionsClient = Nearby.getConnectionsClient(context)
        }
        return connectionsClient!!
    }
}
