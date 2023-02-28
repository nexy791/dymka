package com.ribsky.shop.dialogs.prompt

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.ribsky.common.base.BaseSheet
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.shop.databinding.DialogNotActiveBinding

class SubPromptDialog : BaseSheet<DialogNotActiveBinding>(DialogNotActiveBinding::inflate) {
    override fun initViews(): Unit = with(binding) {
        btnCancel.setOnClickListener { dismiss() }
        btnNext.apply {
            setOnClickListener {
                setFragmentResult(ShopNavigation.RESULT_KEY_PROMPT_SUB, bundleOf())
                dismiss()
            }
        }
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {
        const val TAG = "SubPromptDialog"

        fun newInstance() = SubPromptDialog()
    }
}
