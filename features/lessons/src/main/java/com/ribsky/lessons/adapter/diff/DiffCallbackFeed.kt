package com.ribsky.lessons.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.paragraph.BaseParagraphModel

object DiffCallbackFeed : DiffUtil.ItemCallback<BaseParagraphModel>() {
    override fun areItemsTheSame(
        oldItem: BaseParagraphModel,
        newItem: BaseParagraphModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseParagraphModel,
        newItem: BaseParagraphModel,
    ): Boolean =
        oldItem.percent == newItem.percent
}
