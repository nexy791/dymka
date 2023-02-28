package com.ribsky.feed.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.best.BaseBestWordModel

object DiffCallbackBestWord : DiffUtil.ItemCallback<BaseBestWordModel>() {
    override fun areItemsTheSame(oldItem: BaseBestWordModel, newItem: BaseBestWordModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseBestWordModel,
        newItem: BaseBestWordModel,
    ): Boolean =
        oldItem.title == newItem.title
}
