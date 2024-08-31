package com.ribsky.feed.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.feed.adapter.streak.StreakAdapter


object DiffCallbackPromo : DiffUtil.ItemCallback<BasePromoModel>() {
    override fun areItemsTheSame(
        oldItem: BasePromoModel,
        newItem: BasePromoModel,
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BasePromoModel,
        newItem: BasePromoModel,
    ): Boolean = oldItem.title == newItem.title
}
