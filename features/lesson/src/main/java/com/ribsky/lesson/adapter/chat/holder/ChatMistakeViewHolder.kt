package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatMistakeBinding
import com.ribsky.lesson.model.ChatModel

class ChatMistakeViewHolder(private val binding: ItemChatMistakeBinding) :
    BaseViewHolder<ChatModel.Mistake>(binding) {

    override fun bind(
        item: ChatModel.Mistake,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        var selectedWord = ""
        text.apply {
            setText(item.text + " ", TextView.BufferType.SPANNABLE)
            setOnWordClickListener {
                selectedWord = it.orEmpty()
            }
        }
        btnNext.setOnClickListener {
            if (item.isActive && selectedWord.isNotEmpty()) {
                callback.onMistakeClick(selectedWord)
            }
        }
        text.setDisableHighlight(!item.isActive)
    }

    companion object {
        fun create(parent: ViewGroup): ChatMistakeViewHolder {
            val binding = ItemChatMistakeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatMistakeViewHolder(binding)
        }
    }
}
