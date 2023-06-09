package com.ribsky.common.utils.dynamic

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener

interface DynamicModule {

    sealed class State {
        object NONE : State()
        object DOWNLOADING : State()
        object INSTALLED : State()
        object INSTALL_FINISHED : State()
        class REQUIRES_USER_CONFIRMATION(val state: SplitInstallSessionState) : State()
        class FAILED(val exception: Exception) : State()
    }

    val sessionId: Int

    fun initSplitInstallManager(context: Context)

    fun startInstallModule(
        listener: SplitInstallStateUpdatedListener,
        callbackOnError: (exception: Exception) -> Unit,
        callbackOnSuccess: () -> Unit,
    )

    fun cancelInstallModule()

    fun isModuleInstalled(): Boolean

    fun unregisterListener(listener: SplitInstallStateUpdatedListener)

    fun requestUserConfirmation(
        state: SplitInstallSessionState,
        activityResultRegistry: ActivityResultLauncher<IntentSenderRequest>,
    )
}
