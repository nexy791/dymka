package com.ribsky.bot.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ribsky.bot.adapter.chat.ChatAdapter
import com.ribsky.bot.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.bot.databinding.ItemChatTextLoadingBinding
import com.ribsky.bot.model.ChatModel

class ChatTextLoadingViewHolder(private val binding: ItemChatTextLoadingBinding) :
    BaseViewHolder<ChatModel>(binding) {
    override fun bind(
        item: ChatModel,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
    }

    companion object {
        fun create(parent: ViewGroup): BaseViewHolder<ChatModel> {
            val binding = ItemChatTextLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTextLoadingViewHolder(binding)
        }
    }
}
