package com.ribsky.lessons.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.lessons.model.StarModel

object DiffCallbackStar : DiffUtil.ItemCallback<StarModel>() {
    override fun areItemsTheSame(oldItem: StarModel, newItem: StarModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: StarModel, newItem: StarModel): Boolean =
        oldItem.stars == newItem.stars
}
