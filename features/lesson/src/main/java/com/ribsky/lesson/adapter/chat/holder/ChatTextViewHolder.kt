package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatTextBinding
import com.ribsky.lesson.model.ChatModel

class ChatTextViewHolder(private val binding: ItemChatTextBinding) :
    BaseViewHolder<ChatModel.Text>(binding) {
    override fun bind(
        item: ChatModel.Text,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        text.text = item.text.parseAsHtml()
        root.setOnClickListener {
            callback.onTextClick(item.text)
        }
    }

    companion object {
        fun create(parent: ViewGroup): BaseViewHolder<ChatModel.Text> {
            val binding = ItemChatTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTextViewHolder(binding)
        }
    }
}
