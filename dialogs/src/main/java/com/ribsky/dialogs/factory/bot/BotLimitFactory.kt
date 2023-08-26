package com.ribsky.dialogs.factory.bot

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class BotLimitFactory(
    private val positiveButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Перевищення швидкості! \uD83D\uDC08"
        description =
            "Преміум-користувачі мають до 50 відповідей на день, а звичайні - 20 одноразово"
        icon {
            icon = commonDrawable.ic_outline_offline_bolt_24
        }
        positiveButton {
            text = "Більше"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Закрити"
        }
    }
}
