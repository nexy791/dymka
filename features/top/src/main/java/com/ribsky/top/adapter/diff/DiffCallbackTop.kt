package com.ribsky.top.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.top.model.TopModel

object DiffCallbackTop : DiffUtil.ItemCallback<TopModel>() {
    override fun areItemsTheSame(
        oldItem: TopModel,
        newItem: TopModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: TopModel,
        newItem: TopModel,
    ): Boolean =
        oldItem.position == newItem.position
}
