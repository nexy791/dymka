package com.ribsky.dialogs.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.dialogs.databinding.DialogFullScreenBinding

class FullScreenDialog(
    private val config: Config,
) : BaseSheet<DialogFullScreenBinding>(DialogFullScreenBinding::inflate) {

    data class Config(
        var title: String = "",
        var description: String = "",
        var onDismiss: () -> Unit = {},
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun initViews() = with(binding) {
        tvTitle.text = config.title
        tvDescription.text = config.description

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            sendEmail(
                subject = "dymka повідомити про проблему",
                text = "«${config.description}»"
            )
        }
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        config.onDismiss()
    }

    companion object {
        inline fun create(
            block: Config.() -> Unit,
        ): FullScreenDialog {
            val config = Config().apply(block)
            return FullScreenDialog(config)
        }
    }
}
