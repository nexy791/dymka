package com.ribsky.dialogs.factory.limit

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class LimitFactory(
    private var onConfirm: () -> Unit = {},
    private var onDismiss: () -> Unit = {},
) : DialogFactory {


    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Не так швидко! \uD83E\uDD47"
        description =
            "Пройди спочатку безкоштовні уроки в попередній темі, або розблокуй з підпискою"
        icon {
            icon = commonDrawable.ic_round_speed_24
        }
        positiveButton {
            text = "Добре"
            callback = this@LimitFactory.onConfirm
        }
        negativeButton {
            text = "Преміум"
            callback = this@LimitFactory.onDismiss
        }
    }

}
