package com.ribsky.dialogs.factory.streak

import android.content.DialogInterface
import android.graphics.drawable.AnimatedVectorDrawable
import android.transition.TransitionManager
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ViewExt.Companion.vibrate
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.dialogs.databinding.DialogStreakPassedBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StreakPassedFactory(
    private var onDismiss: (() -> Unit) = {},
) :
    BaseSheet<DialogStreakPassedBinding>(DialogStreakPassedBinding::inflate) {

    override fun initViews(): Unit = with(binding) {
        Analytics.logEvent(Analytics.Event.STREAK_DONE)
        playSound(commonRaw.sound_streak)
        requireContext().vibrate()
        (imageView.drawable as AnimatedVectorDrawable).apply {
            start()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                for (i in 0..10) {
                    progressIndicator.setProgressCompat(i * 10, true)
                    delay(100)
                    if (i == 10) {
                        TransitionManager.beginDelayedTransition(
                            root,
                            MaterialSharedAxis(MaterialSharedAxis.Y, true)
                        )
                        imageView.isGone = true
                        image.isGone = false
                        image2.isGone = false
                        imageViewCheckbox.isGone = false
                    }
                }
            }
        }
        btnNext.setOnClickListener {
            dismiss()
        }
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

        fun create(onDismiss: () -> Unit): StreakPassedFactory {
            return StreakPassedFactory(onDismiss)
        }

    }

}
