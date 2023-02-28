package com.ribsky.lesson.dialogs.action

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.ribsky.common.base.BaseSheet
import com.ribsky.lesson.databinding.DialogMessageActionBinding
import com.ribsky.navigation.features.LessonNavigation
import com.ribsky.navigation.features.LessonNavigation.Companion.KEY_MESSAGE
import com.ribsky.navigation.features.LessonNavigation.Companion.RESULT_KEY_MESSAGE_ACTION_ID

class MessageActionDialog :
    BaseSheet<DialogMessageActionBinding>(DialogMessageActionBinding::inflate) {

    private val args: MessageActionDialogArgs by navArgs()

    private val message: String by lazy { args.messageText }

    override fun initViews() = with(binding) {
        btnShare.setOnClickListener {
            dismissWithResult(LessonNavigation.ResultAction.SHARE(message))
        }
        btnCopy.setOnClickListener {
            dismissWithResult(LessonNavigation.ResultAction.COPY(message))
        }
        btnSupport.setOnClickListener {
            dismissWithResult(LessonNavigation.ResultAction.SUPPORT(message))
        }
    }

    private fun dismissWithResult(result: LessonNavigation.ResultAction) {
        setFragmentResult(
            LessonNavigation.RESULT_KEY_MESSAGE_ACTION,
            bundleOf(RESULT_KEY_MESSAGE_ACTION_ID to result)
        )
        dismiss()
    }

    override fun initObserves() {}

    override fun clear() {}

    companion object {

        const val TAG = "MessageActionDialog"

        fun newInstance(
            message: String,
        ): MessageActionDialog = MessageActionDialog().apply {
            arguments = bundleOf(
                KEY_MESSAGE to message,
            )
        }
    }
}
