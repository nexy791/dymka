package com.ribsky.dialogs.factory.sub

import com.ribsky.analytics.Analytics
import com.ribsky.billing.BillingState
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ViewExt.Companion.formatDays
import com.ribsky.common.utils.ext.ViewExt.Companion.formatHours
import com.ribsky.dialogs.databinding.DialogPickPremBinding

class SubPromptFactory(
    private val state: BillingState? = null,
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
        updateDiscount(state)
    }

    private fun updateDiscount(state: BillingState?) {

        when (state) {
            is BillingState.Discount -> {
                binding.chip.apply {
                    text = "\uD83C\uDF81 Знижка до ${state.date}"
                }
            }

            is BillingState.Infinite -> {
                binding.chip.apply {
                    text = "\uD83C\uDF81 Знижка Назавжди ∞"
                }
            }

            is BillingState.WelcomeDiscount -> {
                binding.chip.apply {
                    text = "\uD83C\uDF81 Залишилось: ${state.date.formatHours()}"
                }
            }

            is BillingState.NoDiscount -> {
                binding.chip.apply {
                    text = "\uD83D\uDCF8 Знижка за сторіз"
                }
            }

            else -> {
                binding.chip.apply {
                    text = "\uD83D\uDCF8 Знижка за сторіз"
                }
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
