package com.ribsky.dialogs.factory.dynamic

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class SuccessInstallFactory(
    private val positiveButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Успішно! \uD83D\uDE3B"
        description = "Кіт вже чекає на тебе в діалозі"
        icon {
            icon = commonDrawable.ic_round_download_done_24
        }
        positiveButton {
            text = "Перейти"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Закрити"
        }
    }
}
