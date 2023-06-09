package com.ribsky.bot.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.bot.adapter.chat.ChatAdapter
import com.ribsky.bot.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.bot.databinding.ItemChatTextBotBinding
import com.ribsky.bot.model.ChatModel

class ChatTextViewHolder(private val binding: ItemChatTextBotBinding) :
    BaseViewHolder<ChatModel>(binding) {
    override fun bind(
        item: ChatModel,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        val item = item as ChatModel.Bot
        text.text = item.message.parseAsHtml()
        root.setOnClickListener {
            callback.onTextClick(item.message)
        }
    }

    companion object {
        fun create(parent: ViewGroup): BaseViewHolder<ChatModel> {
            val binding = ItemChatTextBotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTextViewHolder(binding)
        }
    }
}
