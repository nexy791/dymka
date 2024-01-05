package com.ribsky.bot.adapter.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.bot.R
import com.ribsky.bot.adapter.chat.diff.DiffCallbackChat
import com.ribsky.bot.adapter.chat.holder.ChatTextFromUserViewHolder
import com.ribsky.bot.adapter.chat.holder.ChatTextLoadingViewHolder
import com.ribsky.bot.adapter.chat.holder.ChatTextViewHolder
import com.ribsky.bot.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.bot.model.ChatModel

class ChatAdapter(
    private val photo: String,
    private val callback: OnChatClickListener,
) : ListAdapter<ChatModel, BaseViewHolder<ChatModel>>(DiffCallbackChat) {

    fun interface OnChatClickListener {
        fun onTextClick(text: String, id: Int)
    }

    fun showLoading() {
        submitList(currentList + listOf(ChatModel.Loading))
    }

    fun hideLoading() {
        val list = currentList.toMutableList()
        list.remove(ChatModel.Loading)
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatModel> =
        when (viewType) {
            R.layout.item_chat_text_user -> ChatTextFromUserViewHolder.create(parent)
            R.layout.item_chat_text_bot -> ChatTextViewHolder.create(parent)
            R.layout.item_chat_text_loading -> ChatTextLoadingViewHolder.create(parent)
            else -> error("Unknown type")
        }

    override fun onBindViewHolder(holder: BaseViewHolder<ChatModel>, position: Int) = with(holder) {
        setUserPhoto(photo)
        bind(getItem(position), callback)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ChatModel.User -> R.layout.item_chat_text_user
        is ChatModel.Bot -> R.layout.item_chat_text_bot
        is ChatModel.Loading -> R.layout.item_chat_text_loading
        else -> error("Unknown type")
    }
}
