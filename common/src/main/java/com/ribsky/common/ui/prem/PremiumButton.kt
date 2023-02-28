package com.ribsky.common.ui.prem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ribsky.common.R
import com.ribsky.common.databinding.LayoutPremBtnBinding
import com.ribsky.common.utils.ext.ResourceExt.Companion.color
import com.ribsky.common.utils.ext.ViewExt.Companion.px

class PremiumButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutPremBtnBinding? = null
    private var isPremium: Boolean = false

    init {
        binding = LayoutPremBtnBinding.inflate(LayoutInflater.from(context), this, true)
        init(context)
    }

    fun setPremium(isPremium: Boolean) {
        this.isPremium = isPremium
        init(context)
    }

    private fun init(context: Context) = with(binding!!) {
        if (isPremium) {
            btnShop.apply {
                setCardBackgroundColor(context.color(R.color.indigo))
                strokeWidth = 0.px
            }
            tvHeart.setTextColor(context.color(R.color.white))
        } else {
            btnShop.apply {
                setCardBackgroundColor(context.color(R.color.white))
                strokeWidth = 2.px
            }
            tvHeart.setTextColor(context.color(R.color.indigo))
        }
    }
}
