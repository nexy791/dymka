package com.ribsky.shop.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ribsky.shop.R
import com.ribsky.shop.databinding.ItemShopBinding

class ItemShopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ItemShopBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val styleAttrs = context.theme.obtainStyledAttributes(attrs, R.styleable.ItemShop, 0, 0)
        val title = styleAttrs.getString(R.styleable.ItemShop_shopTitle)
        val description = styleAttrs.getString(R.styleable.ItemShop_shopDescription)
        val icon = styleAttrs.getString(R.styleable.ItemShop_shopIcon)
        val color = styleAttrs.getColor(R.styleable.ItemShop_shopColor, 0)

        binding.apply {
            tvTitle.text = title
            tvDesc.text = description
            imageViewIcon.text = icon
            imageViewBg.setBackgroundColor(color)
        }
    }
}
