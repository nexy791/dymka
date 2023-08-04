package com.ribsky.shop.adapter.cats.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.top.BaseTopModel

object CatsDiffCallback : DiffUtil.ItemCallback<BaseTopModel>() {
    override fun areItemsTheSame(
        oldItem: BaseTopModel,
        newItem: BaseTopModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseTopModel,
        newItem: BaseTopModel,
    ): Boolean =
        oldItem.image == newItem.image
}