package com.ribsky.settings.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.settings.model.Settings

object DiffCallbackSettings : DiffUtil.ItemCallback<Settings>() {
    override fun areItemsTheSame(
        oldItem: Settings,
        newItem: Settings,
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Settings,
        newItem: Settings,
    ): Boolean =
        oldItem.id == newItem.id
}
