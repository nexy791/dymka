package com.ribsky.dialogs.factory.notes

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class NoteLimitFactory(
    private var onConfirm: () -> Unit = {},
    private var onDismiss: () -> Unit = {},
) : DialogFactory {


    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Розмір конспекту \uD83D\uDCDD"
        description =
            "Звичайні користувачі можуть додавати до 20 повідомлень в конспект до однієї теми, а Преміум – нескінчено"
        icon {
            icon = commonDrawable.ic_outline_note_alt_24
        }
        positiveButton {
            text = "Добре"
            callback = this@NoteLimitFactory.onConfirm
        }
        negativeButton {
            text = "Преміум"
            callback = this@NoteLimitFactory.onDismiss
        }
    }

}
