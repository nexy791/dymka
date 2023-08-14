package com.ribsky.dialogs.factory.notes

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class NoteHowToAddFactory : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Конспект \uD83D\uDCDD"
        description =
            "Натисни на повідомлення, щоб додати його в конспект або видалити"
        icon {
            icon = commonDrawable.ic_outline_note_alt_24
        }

        positiveButton {
            text = "Продовжити"
        }
    }
}
