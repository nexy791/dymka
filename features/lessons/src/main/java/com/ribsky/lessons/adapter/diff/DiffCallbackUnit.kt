package com.ribsky.lessons.adapter.diff

import androidx.recyclerview.widget.DiffUtil

object DiffCallbackUnit : DiffUtil.ItemCallback<Unit>() {
    override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean = true

    override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
}
