package com.ribsky.feed.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.paragraph.BaseParagraphModel

object DiffCallbackParagraph : DiffUtil.ItemCallback<BaseParagraphModel>() {
    override fun areItemsTheSame(
        oldItem: BaseParagraphModel,
        newItem: BaseParagraphModel,
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: BaseParagraphModel,
        newItem: BaseParagraphModel,
    ): Boolean =
        oldItem.percent == newItem.percent && oldItem.id == newItem.id
}
