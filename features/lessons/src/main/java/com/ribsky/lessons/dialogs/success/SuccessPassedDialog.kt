package com.ribsky.lessons.dialogs.success

import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.party.Party
import com.ribsky.lessons.databinding.DialogSuccessPassedBinding

class SuccessPassedDialog :
    BaseSheet<DialogSuccessPassedBinding>(DialogSuccessPassedBinding::inflate) {

    override fun initViews() {
        initKonfetti()
        initBtns()
    }

    private fun initKonfetti() = with(binding) {
        konfettiView.start(Party.rain)
    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener { dismiss() }
    }

    override fun clear() {
    }

    override fun initObserves() {
    }
}
