package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatAnswerBinding
import com.ribsky.lesson.model.ChatModel

class ChatAnswerViewHolder(private val binding: ItemChatAnswerBinding) :
    BaseViewHolder<ChatModel.Answer>(binding) {
    override fun bind(
        item: ChatModel.Answer,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        text.text = item.text.parseAsHtml()

        root.setOnClickListener {
            callback.onTextClick(item.text)
        }
    }

    companion object {
        fun create(parent: ViewGroup): BaseViewHolder<ChatModel.Answer> {
            val binding = ItemChatAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatAnswerViewHolder(binding)
        }
    }
}
