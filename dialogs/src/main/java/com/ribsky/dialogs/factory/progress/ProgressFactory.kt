package com.ribsky.dialogs.factory.progress

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.LargeImageDialog
import com.ribsky.dialogs.base.LargeImageDialog.Companion.negativeButton
import com.ribsky.dialogs.base.LargeImageDialog.Companion.positiveButton

class ProgressFactory(
    private val positiveButtonCallback: () -> Unit = {},
    private val negativeButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): LargeImageDialog = LargeImageDialog.create {
        title = "Майже готово! \uD83D\uDE3C"
        description =
            "Мені потрібно ще трохи часу, щоб завершити всі свої кошачі справи \uD83D\uDE3C"
        image = commonDrawable.cat_time

        positiveButton {
            text = "Допомогти"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Закрити"
            callback = negativeButtonCallback
        }
    }
}
