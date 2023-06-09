package com.ribsky.dymka.dialogs

import com.ribsky.common.base.BaseSheet
import com.ribsky.dymka.databinding.DialogLoadingBinding

class LoadingDialog :
    BaseSheet<DialogLoadingBinding>(DialogLoadingBinding::inflate) {

    override fun initViews() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun clear() {
    }

    override fun initObserves() {
    }

    companion object {

        fun newInstance() = LoadingDialog()
    }
}
