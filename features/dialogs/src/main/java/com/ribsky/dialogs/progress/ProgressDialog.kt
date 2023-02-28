package com.ribsky.dialogs.progress

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogInProgressBinding
import com.ribsky.navigation.features.DialogsNavigation

class ProgressDialog : BaseSheet<DialogInProgressBinding>(DialogInProgressBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        btnNext.setOnClickListener {
            setFragmentResult(
                DialogsNavigation.RESULT_KEY_PROGRESS,
                bundleOf()
            )
            dismiss()
        }
        btnCancel.setOnClickListener { dismiss() }
    }

    override fun initObserves() {}

    override fun clear() {
    }
}
