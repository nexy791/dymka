package com.ribsky.dialogs.factory.error

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class ConnectionErrorFactory(
    private val positiveButtonCallback: () -> Unit = {},
    private val negativeButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Немає інтернету \uD83D\uDCF6"
        description = "Перевірте підключення до інтернету та спробуй ще раз"
        icon {
            icon = commonDrawable.ic_round_wifi_off_24
        }
        positiveButton {
            text = "Налаштування"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Закрити"
            callback = negativeButtonCallback
        }
    }
}
