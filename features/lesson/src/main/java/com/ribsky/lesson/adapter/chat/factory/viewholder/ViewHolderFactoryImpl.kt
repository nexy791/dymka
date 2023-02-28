package com.ribsky.lesson.adapter.chat.factory.viewholder

import android.view.ViewGroup
import com.ribsky.lesson.R
import com.ribsky.lesson.adapter.chat.holder.*
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.model.ChatModel

class ViewHolderFactoryImpl : ViewHolderFactory {
    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatModel> {
        return when (viewType) {
            R.layout.item_chat_text -> ChatTextViewHolder.create(parent)
            R.layout.item_chat_text_spending -> ChatTextFromUserViewHolder.create(parent)
            R.layout.item_chat_translate_text -> ChatTranslateViewHolder.create(parent)
            R.layout.item_chat_chips -> ChatChipsViewHolder.create(parent)
            R.layout.item_chat_test -> ChatTestHolderView.create(parent)
            R.layout.item_chat_mistake -> ChatMistakeViewHolder.create(parent)
            R.layout.item_chat_image -> ChatImageHolderView.create(parent)
            R.layout.item_chat_answer -> ChatAnswerViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type")
        } as BaseViewHolder<ChatModel>
    }
}
