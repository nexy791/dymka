package com.ribsky.lesson.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.lesson.model.ChatModel

object DiffCallbackChat : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean =
        oldItem.id == newItem.id
}
