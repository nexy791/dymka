package com.ribsky.game.ui.lobby

import android.util.Log
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ViewExt.Companion.toast
import com.ribsky.game.databinding.ActivityLobbyBinding
import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.navigation.features.GameNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LobbyActivity :
    BaseActivity<GameNavigation, LobbyViewModel, ActivityLobbyBinding>(ActivityLobbyBinding::inflate) {

    override val viewModel: LobbyViewModel by viewModel()
    override val navigation: GameNavigation by inject()

    private val state: GameNavigation.LobbyState by lazy {
        intent.getParcelableExtra(GameNavigation.KEY_LOBBY_STATE)!!
    }

    private val userPhoto: String by lazy {
        intent.getStringExtra(GameNavigation.KEY_LOBBY_IMAGE)!!
    }

    private val pickedId: String by lazy {
        intent.getStringExtra(GameNavigation.KEY_LOBBY_ID) ?: ""
    }

    private val connectionManager: ConnectionManager by inject()

    private val connectionResultCallback: ConnectionManager.ResultCallback =
        object : ConnectionManager.ResultCallback {
            override fun onSuccess() {
            }

            override fun onFailure(error: Exception) {
                finish()
            }
        }

    private val endpointDiscoveryCallback: ConnectionManager.DiscoveryListener =
        object : ConnectionManager.DiscoveryListener {
            override fun onEndpointFound(endpointId: String) {
                connectionManager.requestConnection(endpointId, connectionResultCallback)
            }

            override fun onEndpointLost(endpointId: String) {
            }
        }

    private val connectionLifecycleCallback: ConnectionManager.ConnectionListener =
        object : ConnectionManager.ConnectionListener {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                showConfirmDialog(connectionInfo.authenticationDigits, endpointId)
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> startGame(endpointId)
                    else -> finish()
                }
            }

            override fun onDisconnected(endpointId: String) {
            }
        }

    private fun updateState(state: GameNavigation.LobbyState) {
        when (state) {
            GameNavigation.LobbyState.WAITING -> switchToAdverting()
            GameNavigation.LobbyState.SEARCHING -> switchToDiscovery()
        }
    }

    private fun switchToAdverting() = with(binding) {
        tvTitle.text = "Очікування \uD83D\uDC23"
        tvPrice.text =
            "Попроси друга увімкнути режим пошуку. Ваші телефони мають бути на відстані до 10 метрів"
        connectionManager.stopAdvertising()
        connectionManager.stopDiscovery()
        connectionManager.startAdvertising(connectionResultCallback)
    }

    private fun switchToDiscovery() = with(binding) {
        tvTitle.text = "Пошук \uD83D\uDD0D"
        tvPrice.text =
            "Попроси друга увімкнути режим очікування. Ваші телефони мають бути на відстані до 10 метрів"
        connectionManager.stopAdvertising()
        connectionManager.stopDiscovery()
        connectionManager.startDiscovery(connectionResultCallback)
    }

    private fun showConfirmDialog(code: String, endPointId: String) {
        navigation.navigateConfirmDialog(code, endPointId)
    }

    private fun startGame(endPointId: String) {
        navigation.navigateGame(endPointId, pickedId, state == GameNavigation.LobbyState.WAITING)
    }

    override fun initView() = with(binding) {
        initConnectionManager()
        updateState(state)
        rootRv.startRipple()
        ivAccount.load(userPhoto) {
            transformations(CircleCropTransformation())
        }
        btnExit.setOnClickListener {
            finish()
        }
    }

    private fun initConnectionManager() {
        connectionManager.apply {
            setConnectionListener(connectionLifecycleCallback)
            setDiscoveryListener(endpointDiscoveryCallback)
            setConnectionResultCallback(connectionResultCallback)
        }
    }

    override fun initObs() = with(viewModel) {
        supportFragmentManager.setFragmentResultListener(
            GameNavigation.RESULT_KEY_RESULT_CONFIRM,
            this@LobbyActivity
        ) { _, bundle ->
            val result =
                bundle.getParcelable<GameNavigation.ConfirmResult>(GameNavigation.RESULT_KEY_RESULT_CONFIRM_ACTION)!!
            when (result) {
                is GameNavigation.ConfirmResult.REJECT -> connectionManager.rejectConnection(result.endPointId)
                is GameNavigation.ConfirmResult.ACCEPT -> connectionManager.acceptConnection(result.endPointId)
            }
        }
    }

    override fun clear() {
        binding.rootRv.stopRipple()
        connectionManager.stop()
    }
}
