package com.ribsky.feed.adapter.promo.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.feed.adapter.promo.PromoAdapter
import com.ribsky.feed.databinding.ItemPromoBinding

class PromoViewHolder(private val binding: ItemPromoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BasePromoModel, callback: PromoAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener { callback.onClick(item.link) }
            materialCardView.setCardBackgroundColor(item.background.toColor())
            tvTitle.apply {
                text = item.title
                setTextColor(item.color.toColor())
            }
            tvDescription.apply {
                text = item.description
                setTextColor(item.color.toColor())
                alpha = 0.7f
            }
        }

    companion object {
        fun create(parent: ViewGroup): PromoViewHolder {
            val binding = ItemPromoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return PromoViewHolder(binding)
        }
    }
}
