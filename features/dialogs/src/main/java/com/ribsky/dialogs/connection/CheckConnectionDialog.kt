package com.ribsky.dialogs.connection

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogCheckConnectionBinding
import com.ribsky.navigation.features.DialogsNavigation.Companion.RESULT_KEY_CONNECTION

class CheckConnectionDialog :
    BaseSheet<DialogCheckConnectionBinding>(DialogCheckConnectionBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        btnNext.setOnClickListener {
            setFragmentResult(RESULT_KEY_CONNECTION, bundleOf())
            dismiss()
        }
    }

    override fun clear() {
    }


    override fun initObserves() {
    }
}
