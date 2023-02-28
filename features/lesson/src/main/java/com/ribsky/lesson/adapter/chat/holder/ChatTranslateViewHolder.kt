package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatTranslateTextBinding
import com.ribsky.lesson.model.ChatModel

class ChatTranslateViewHolder(private val binding: ItemChatTranslateTextBinding) :
    BaseViewHolder<ChatModel.TranslateText>(binding) {
    override fun bind(
        item: ChatModel.TranslateText,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        text.text = item.text.parseAsHtml()
        root.setOnClickListener {
            callback.onTextClick(item.text)
        }
    }

    companion object {
        fun create(parent: ViewGroup): BaseViewHolder<ChatModel.TranslateText> {
            val binding = ItemChatTranslateTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTranslateViewHolder(binding)
        }
    }
}
