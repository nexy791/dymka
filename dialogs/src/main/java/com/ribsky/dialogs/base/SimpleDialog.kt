package com.ribsky.dialogs.base

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ribsky.common.base.BaseSheet
import com.ribsky.dialogs.databinding.DialogSimpleBinding

class SimpleDialog(
    private val config: Config,
) : BaseSheet<DialogSimpleBinding>(DialogSimpleBinding::inflate) {

    data class Config(
        var title: String = "",
        var description: String = "",
        var descriptionSize: Float = 16f,
        var imageColor: Int? = Color.parseColor("#90CAF9"),

        var icon: Icon? = null,
        var positiveButton: ButtonConfig? = null,
        var negativeButton: ButtonConfig? = null,

        var isCancelable: Boolean = true,
        var onDismiss: (() -> Unit)? = null,

        )

    data class Icon(
        var icon: Int? = null,
        var color: Int = Color.parseColor("#ffffff"),
        var isAnimated: Boolean = true,
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
        imageView.apply {
            config.icon?.icon?.let { setImageResource(it) }
            config.icon?.color?.let { setColorFilter(it, PorterDuff.Mode.SRC_IN) }
            config.icon?.isAnimated?.let {
                if (it) {
                    alpha = 0f
                    animate()
                        .scaleX(1.15f)
                        .scaleY(1.15f)
                        .alpha(1f)
                        .translationY(-10f)
                        .setStartDelay(300)
                        .setDuration(300)
                        .withEndAction {
                            animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .translationY(0f)
                                .setDuration(300)
                                .start()
                        }.start()
                }
            }
        }
        image.setBackgroundColor(config.imageColor ?: Color.parseColor("#90CAF9"))

        tvTitle.text = config.title
        tvDescription.apply {
            text = config.description
            textSize = config.descriptionSize
        }

        btnNext.apply {
            config.positiveButton?.let {
                text = it.text
                it.textColor?.let { color -> setTextColor(color) }
                it.backgroundColor?.let { color -> setBackgroundColor(color) }
                it.icon?.let { icon ->
                    setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
                }
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

    override fun initObserves() {
    }

    override fun clear() {
    }

    companion object {
        inline fun create(
            block: Config.() -> Unit,
        ): SimpleDialog {
            val config = Config().apply(block)
            return SimpleDialog(config)
        }

        fun Config.icon(block: Icon.() -> Unit) {
            val iconConfig = Icon().apply(block)
            icon = iconConfig
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
