package com.ribsky.dialogs.base

import android.content.DialogInterface
import com.ribsky.common.base.BaseSheetFullScreen
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.dialogs.databinding.DialogFullScreenBinding

class FullScreenDialog(
    private val config: Config? = null,
) : BaseSheetFullScreen<DialogFullScreenBinding>(DialogFullScreenBinding::inflate) {

    data class Config(
        var title: String = "",
        var description: String = "",
        var onDismiss: () -> Unit = {},
    )

    override fun initViews() = with(binding) {
        if (config == null) {
            dismiss()
            return@with
        }
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
        config?.onDismiss?.invoke()
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
