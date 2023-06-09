package com.ribsky.bot.adapter.chat.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.bot.model.ChatModel

object DiffCallbackChat : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean =
        oldItem.id == newItem.id
}
