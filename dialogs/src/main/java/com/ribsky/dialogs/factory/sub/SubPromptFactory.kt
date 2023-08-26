package com.ribsky.dialogs.factory.sub

import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogPickPremBinding

class SubPromptFactory(
    private val date: String? = null,
    private val positiveButtonCallback: () -> Unit = {},
) : BaseSheet<DialogPickPremBinding>(DialogPickPremBinding::inflate) {

    override fun initViews() = with(binding) {
        Analytics.logEvent(Analytics.Event.PREMIUM_OPEN_DIALOG)
        btnNo.setOnClickListener {
            dismiss()
        }
        btnBuy.setOnClickListener {
            positiveButtonCallback.invoke()
            dismiss()
        }
        updateDiscount(date)
    }

    private fun updateDiscount(date: String?) {
        if (!date.isNullOrEmpty()) {
            binding.chip.apply {
                text = "\uD83C\uDF81 Знижка $date"
            }
        } else {
            binding.chip.apply {
                text = "\uD83D\uDCF8 Знижка за сторіз"
            }
        }
        binding.chip.setOnClickListener {
            positiveButtonCallback.invoke()
            dismiss()
        }
    }

    override fun initObserves() {

    }

    override fun clear() {

    }
}
