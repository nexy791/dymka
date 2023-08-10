package com.ribsky.dialogs.factory.stars

import android.content.DialogInterface
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ViewExt.Companion.formatStars
import com.ribsky.common.utils.ext.ViewExt.Companion.vibrate
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.dialogs.databinding.DialogStarsBinding

class SuccessStarsFactory(
    private val stars: Float,
    private var onDismiss: (() -> Unit),
) :
    BaseSheet<DialogStarsBinding>(DialogStarsBinding::inflate) {

    override fun initViews(): Unit = with(binding) {
        Analytics.logEvent(Analytics.Event.STARS_COLLECTED(stars.toInt()))
        playSound(commonRaw.sound_stars)
        vibrate()
        btnNext.setOnClickListener {
            dismiss()
        }
        if (stars.toInt() != 0) {
            tvTitle.text = "Ще більше зірок! ⭐"
            tvDescription.text = "Урок пройдено на ${stars.toInt().formatStars()}!"
        } else {
            tvTitle.text = "Давай ще раз! ⭐"
            tvDescription.text = "Пройди цей урок без помилок і отримай зірки!"
        }
        ratingBar.setRating(stars)
    }

    override fun initObserves() {

    }

    override fun clear() {

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }

    companion object {

        fun create(stars: Float, onDismiss: (() -> Unit)): SuccessStarsFactory =
            SuccessStarsFactory(stars, onDismiss)
    }

}
