package com.ribsky.game.dialogs

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.ribsky.common.base.BaseSheet
import com.ribsky.game.databinding.DialogGamesConfirmBinding
import com.ribsky.navigation.features.GameNavigation
import com.ribsky.navigation.features.GameNavigation.Companion.RESULT_KEY_RESULT_CONFIRM
import com.ribsky.navigation.features.GameNavigation.Companion.RESULT_KEY_RESULT_CONFIRM_ACTION

class GameConfirmDialog :
    BaseSheet<DialogGamesConfirmBinding>(DialogGamesConfirmBinding::inflate) {

    private val args: GameConfirmDialogArgs by navArgs()

    private val code by lazy { args.code }

    private val endPointId by lazy { args.endPointId }

    override fun initViews(): Unit = with(binding) {
        btnCancel.setOnClickListener {
            dismissWithResult(GameNavigation.ConfirmResult.REJECT(endPointId))
        }
        btnNext.setOnClickListener {
            dismissWithResult(GameNavigation.ConfirmResult.ACCEPT(endPointId))
        }
        tvTitle.text = code
    }

    private fun dismissWithResult(result: GameNavigation.ConfirmResult) {
        setFragmentResult(
            RESULT_KEY_RESULT_CONFIRM,
            bundleOf(RESULT_KEY_RESULT_CONFIRM_ACTION to result)
        )
        dismiss()
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {
        const val TAG = "GameConfirmDialog"
        fun newInstance(code: String, endPointId: String): GameConfirmDialog {
            return GameConfirmDialog().apply {
                arguments = bundleOf(
                    GameNavigation.KEY_CONFIRM_CODE to code,
                    GameNavigation.KEY_CONFIRM_END_POINT_ID to endPointId
                )
            }
        }
    }
}
