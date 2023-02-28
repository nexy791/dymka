package com.ribsky.feed.adapter.diff

import androidx.recyclerview.widget.DiffUtil

object DiffCallbackPrem : DiffUtil.ItemCallback<Boolean>() {
    override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Boolean,
        newItem: Boolean,
    ): Boolean =
        oldItem == newItem
}
