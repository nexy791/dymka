package com.ribsky.bot.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.ribsky.bot.adapter.chat.ChatAdapter
import com.ribsky.bot.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.bot.databinding.ItemChatTextUserBinding
import com.ribsky.bot.model.ChatModel
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage

class ChatTextFromUserViewHolder(private val binding: ItemChatTextUserBinding) :
    BaseViewHolder<ChatModel>(binding) {

    override fun bind(
        item: ChatModel,
        callback: ChatAdapter.OnChatClickListener,
    ): Unit = with(binding) {
        val item = item as ChatModel.User
        text.text = item.message.parseAsHtml()
        shapeableImageView.loadImage(photoUrl)
    }

    companion object {
        fun create(parent: ViewGroup): ChatTextFromUserViewHolder {
            val binding = ItemChatTextUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTextFromUserViewHolder(binding)
        }
    }
}
