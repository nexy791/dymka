package com.ribsky.paywall.adapter.more.diff

import androidx.recyclerview.widget.DiffUtil

object MoreCallback : DiffUtil.ItemCallback<Unit>() {
    override fun areItemsTheSame(
        oldItem: Unit,
        newItem: Unit,
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: Unit,
        newItem: Unit,
    ): Boolean = true
}