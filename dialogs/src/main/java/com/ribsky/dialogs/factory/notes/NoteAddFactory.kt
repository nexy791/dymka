package com.ribsky.dialogs.factory.notes

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class NoteAddFactory : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Збережено \uD83D\uDCDD"
        description =
           "Повідомлення було додано в конспект"
        icon {
            icon = commonDrawable.ic_outline_note_alt_24
        }

        positiveButton {
            text = "Продовжити"
        }
    }
}
