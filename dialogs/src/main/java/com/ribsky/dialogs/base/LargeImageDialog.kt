package com.ribsky.dialogs.base

import android.content.DialogInterface
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogLargeBinding

class LargeImageDialog(
    private val config: Config? = null,
) : BaseSheet<DialogLargeBinding>(DialogLargeBinding::inflate) {

    data class Config(
        var title: String = "",
        var description: String = "",
        var image: Int? = null,

        var positiveButton: ButtonConfig? = null,
        var negativeButton: ButtonConfig? = null,

        var isCancelable: Boolean = true,
        var onDismiss: (() -> Unit)? = null,

        )

    data class ButtonConfig(
        var text: String = "",
        var textColor: Int? = null,
        var backgroundColor: Int? = null,
        var isDismiss: Boolean = true,
        var isShow: Boolean = true,
        var icon: Int? = null,
        var callback: (() -> Unit)? = null,
    )

    override fun initViews(): Unit = with(binding) {
        if (config == null) {
            dismiss()
            return
        }
        imageView.setImageResource(config.image ?: 0)
        tvTitle.text = config.title
        tvDescription.text = config.description

        btnNext.apply {
            config.positiveButton?.let {
                text = it.text
                it.textColor?.let { color -> setTextColor(color) }
                it.backgroundColor?.let { color -> setBackgroundColor(color) }
                it.icon?.let { icon -> setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0) }
                setOnClickListener { _ ->
                    it.callback?.invoke()
                    if (it.isDismiss) dismiss()
                }
                visibility = if (it.isShow) VISIBLE else GONE
            } ?: run {
                visibility = GONE
            }
        }

        btnCancel.apply {
            config.negativeButton?.let {
                text = it.text
                it.textColor?.let { color -> setTextColor(color) }
                it.backgroundColor?.let { color -> setBackgroundColor(color) }
                it.icon?.let { icon -> setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0) }
                setOnClickListener { _ ->
                    it.callback?.invoke()
                    if (it.isDismiss) dismiss()
                }
                visibility = if (it.isShow) VISIBLE else GONE
            } ?: run {
                visibility = GONE
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        config?.onDismiss?.invoke()
    }

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {
        inline fun create(
            block: Config.() -> Unit,
        ): LargeImageDialog {
            val config = Config().apply(block)
            return LargeImageDialog(config)
        }

        fun Config.positiveButton(block: ButtonConfig.() -> Unit) {
            val buttonConfig = ButtonConfig().apply(block)
            positiveButton = buttonConfig
        }

        fun Config.negativeButton(block: ButtonConfig.() -> Unit) {
            val buttonConfig = ButtonConfig().apply(block)
            negativeButton = buttonConfig
        }
    }
}
