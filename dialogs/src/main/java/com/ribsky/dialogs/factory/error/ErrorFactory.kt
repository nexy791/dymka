package com.ribsky.dialogs.factory.error

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ribsky.analytics.Analytics
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.FullScreenDialog
import org.koin.java.KoinJavaComponent.inject

class ErrorFactory(
    private var error: String?,
    private var onDismiss: () -> Unit = {},
) : DialogFactory {

    private val crashlytics: FirebaseCrashlytics by inject(FirebaseCrashlytics::class.java)

    override fun createDialog(): FullScreenDialog = FullScreenDialog.create {
        Analytics.logEvent(Analytics.Event.ERROR)
        crashlytics.recordException(Exception(error))
        title = funnyErrors.random()
        description = error.orEmpty().ifEmpty { "Невідома помилка" }
        onDismiss = this@ErrorFactory.onDismiss
    }

    companion object {
        private val funnyErrors =
            listOf(
                "От халепа! \uD83D\uDE3F",
                "Ну як так! \uD83D\uDC08\u200D⬛",
                "Я заплутався! \uD83D\uDE48",
                "Що це було? \uD83D\uDC7B",
                "От я нестелепа! \uD83D\uDE40",
            )

        fun FragmentManager.showErrorDialog(
            error: String?,
            onDismiss: () -> Unit = {},
        ) {
            if (this.isDestroyed) return
            runCatching {
                ErrorFactory(error, onDismiss).createDialog().show(this, "error")
            }
        }

        fun AppCompatActivity.showErrorDialog(
            message: String?,
            onDismiss: () -> Unit = {},
        ) {
            supportFragmentManager.showErrorDialog(message, onDismiss)
        }

        fun Fragment.showErrorDialog(
            message: String?,
            onDismiss: () -> Unit = {},
        ) {
            (activity as? AppCompatActivity)?.showErrorDialog(message, onDismiss)
        }
    }
}
