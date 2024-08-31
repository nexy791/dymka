package com.ribsky.dialogs.factory.score

import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatStarsV2
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class ScoreFactory (
    private var onConfirm: () -> Unit = {},
    private var onDismiss: () -> Unit = {},
) : DialogFactory {


    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Балів за тести"
        description = "Проходь тести та отримуй бали, щоб підніматись у рейтингу"
        icon {
            icon = commonDrawable.ic_outline_collections_bookmark_24
        }
        positiveButton {
            text = "Добре"
            callback = this@ScoreFactory.onConfirm
        }
        negativeButton {
            text = "Преміум"
            callback = this@ScoreFactory.onDismiss
        }
    }

}
