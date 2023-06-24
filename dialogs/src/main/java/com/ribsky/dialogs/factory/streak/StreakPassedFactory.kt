package com.ribsky.dialogs.factory.streak

import android.graphics.drawable.AnimatedVectorDrawable
import android.transition.TransitionManager
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogStreakPassedBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StreakPassedFactory :
    BaseSheet<DialogStreakPassedBinding>(DialogStreakPassedBinding::inflate) {

    override fun initViews(): Unit = with(binding) {
        Analytics.logEvent(Analytics.Event.STREAK_DONE)
        (imageView.drawable as AnimatedVectorDrawable).apply {
            start()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                for (i in 0..10) {
                    binding.progressIndicator.setProgressCompat(i * 10, true)
                    delay(100)
                    if (i == 10) {
                        TransitionManager.beginDelayedTransition(
                            binding.root,
                            MaterialSharedAxis(MaterialSharedAxis.Y, true)
                        )
                        binding.imageView.isGone = true
                        binding.image.isGone = false
                        binding.image2.isGone = false
                        binding.imageViewCheckbox.isGone = false
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

    companion object {

        fun create(): StreakPassedFactory {
            return StreakPassedFactory()
        }

    }

}
