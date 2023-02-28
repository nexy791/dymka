package com.ribsky.top.adapter.diff

import androidx.recyclerview.widget.DiffUtil

object DiffCallbackEmpty : DiffUtil.ItemCallback<Unit>() {
    override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean =
        false

    override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean =
        false
}
