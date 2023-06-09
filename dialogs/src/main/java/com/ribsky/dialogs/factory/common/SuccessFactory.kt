package com.ribsky.dialogs.factory.common

import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.LargeImageDialog
import com.ribsky.dialogs.base.LargeImageDialog.Companion.positiveButton

class SuccessFactory : DialogFactory {

    override fun createDialog(): LargeImageDialog = LargeImageDialog.create {
        Analytics.logEvent(Analytics.Event.END_LESSON)
        title = "Так тримати! \uD83D\uDCDA"
        description = "Урок успішно пройдено.\nВітаю з новими знаннями!"
        image = commonDrawable.cat_book

        positiveButton {
            text = "Продовжити"
        }
    }
}
