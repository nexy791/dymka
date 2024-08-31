package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatTextSpendingBinding
import com.ribsky.lesson.model.ChatModel

class ChatTextFromUserViewHolder(private val binding: ItemChatTextSpendingBinding) :
    BaseViewHolder<ChatModel.TextFromUser>(binding) {

    override fun bind(
        item: ChatModel.TextFromUser,
        callback: ChatAdapter.OnChatClickListener,
    ): Unit = with(binding) {
        text.text = item.text.parseAsHtml()
        shapeableImageView.loadImage(photoUrl)
    }

    companion object {
        fun create(parent: ViewGroup): ChatTextFromUserViewHolder {
            val binding = ItemChatTextSpendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTextFromUserViewHolder(binding)
        }
    }
}
