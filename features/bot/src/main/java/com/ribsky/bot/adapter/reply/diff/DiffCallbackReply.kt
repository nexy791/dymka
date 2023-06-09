package com.ribsky.bot.adapter.reply.diff

import androidx.recyclerview.widget.DiffUtil

object DiffCallbackReply : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}
