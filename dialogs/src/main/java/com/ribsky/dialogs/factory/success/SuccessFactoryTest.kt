package com.ribsky.dialogs.factory.success

import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.LargeImageDialog
import com.ribsky.dialogs.base.LargeImageDialog.Companion.positiveButton

class SuccessFactoryTest(
    private val count: Int,
    private var onDismiss: (() -> Unit),
) : DialogFactory {

    override fun createDialog(): LargeImageDialog = LargeImageDialog.create {
        Analytics.logEvent(Analytics.Event.END_TEST)
        playSound(commonRaw.sound_end)
        title = "+$count балів! \uD83D\uDCDA"
        description = "Вітаю, ти отримав $count балів за виконання тесту!"
        image = commonDrawable.cat_book

        positiveButton {
            text = "Продовжити"
        }
        onDismiss = this@SuccessFactoryTest.onDismiss
    }
}
