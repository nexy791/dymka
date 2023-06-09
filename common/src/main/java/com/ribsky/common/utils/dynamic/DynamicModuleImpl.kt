package com.ribsky.common.utils.dynamic

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener

class DynamicModuleImpl : DynamicModule {

    companion object {
        private const val MODULE_NAME = "bot"
    }

    private var mSessionId = 0
    private var splitInstallManager: SplitInstallManager? = null
    override val sessionId: Int get() = mSessionId

    override fun initSplitInstallManager(context: Context) {
        splitInstallManager = SplitInstallManagerFactory.create(context)
    }

    override fun startInstallModule(
        listener: SplitInstallStateUpdatedListener,
        callbackOnError: (exception: Exception) -> Unit,
        callbackOnSuccess: () -> Unit,
    ) {

        val request = SplitInstallRequest.newBuilder()
            .addModule(MODULE_NAME)
            .build()

        splitInstallManager?.registerListener(listener)
        splitInstallManager
            ?.startInstall(request)
            ?.addOnSuccessListener { sessionId ->
                mSessionId = sessionId
                callbackOnSuccess()
            }
            ?.addOnFailureListener {
                callbackOnError(it)
                cancelInstallModule()
            }
    }

    override fun cancelInstallModule() {
        splitInstallManager?.cancelInstall(mSessionId)
    }

    override fun isModuleInstalled(): Boolean {
        return splitInstallManager?.installedModules?.contains(MODULE_NAME) ?: false
    }

    override fun unregisterListener(listener: SplitInstallStateUpdatedListener) {
        splitInstallManager?.unregisterListener(listener)
    }

    override fun requestUserConfirmation(state: SplitInstallSessionState, activityResultRegistry: ActivityResultLauncher<IntentSenderRequest>) {
        splitInstallManager?.startConfirmationDialogForResult(
            state,
            activityResultRegistry
        )
    }
}
