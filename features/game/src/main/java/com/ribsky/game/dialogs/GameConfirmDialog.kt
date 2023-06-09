package com.ribsky.game.dialogs

import androidx.core.os.bundleOf
import com.ribsky.common.base.BaseSheet
import com.ribsky.game.databinding.DialogGamesConfirmBinding

class GameConfirmDialog(
    private val positiveButtonCallback: () -> Unit,
    private val negativeButtonCallback: () -> Unit,
) :
    BaseSheet<DialogGamesConfirmBinding>(DialogGamesConfirmBinding::inflate) {

    private val code by lazy {
        arguments?.getString(KEY_CONFIRM_CODE) ?: ""
    }

    override fun initViews(): Unit = with(binding) {
        btnCancel.setOnClickListener {
            negativeButtonCallback()
            dismiss()
        }
        btnNext.setOnClickListener {
            positiveButtonCallback()
            dismiss()
        }
        tvTitle.text = code
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {

        private const val KEY_CONFIRM_CODE = "code"

        fun newInstance(
            code: String,
            positiveButtonCallback: () -> Unit,
            negativeButtonCallback: () -> Unit,
        ): GameConfirmDialog {
            return GameConfirmDialog(positiveButtonCallback, negativeButtonCallback).apply {
                arguments = bundleOf(KEY_CONFIRM_CODE to code)
            }
        }
    }
}
