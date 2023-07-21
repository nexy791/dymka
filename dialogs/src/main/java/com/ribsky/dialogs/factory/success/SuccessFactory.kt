package com.ribsky.dialogs.factory.success

import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.LargeImageDialog
import com.ribsky.dialogs.base.LargeImageDialog.Companion.positiveButton

class SuccessFactory(
    private var onDismiss: (() -> Unit),
) : DialogFactory {

    override fun createDialog(): LargeImageDialog = LargeImageDialog.create {
        Analytics.logEvent(Analytics.Event.END_LESSON)
        playSound(commonRaw.sound_end)
        title = "Так тримати! \uD83D\uDCDA"
        description = "Урок успішно пройдено.\nВітаю з новими знаннями!"
        image = commonDrawable.cat_book

        positiveButton {
            text = "Продовжити"
        }
        onDismiss = this@SuccessFactory.onDismiss
    }
}
