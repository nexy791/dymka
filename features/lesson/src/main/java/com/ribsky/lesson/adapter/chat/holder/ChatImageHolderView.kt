package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.databinding.ItemChatImageBinding
import com.ribsky.lesson.model.ChatModel
import org.koin.java.KoinJavaComponent.inject

class ChatImageHolderView(private val binding: ItemChatImageBinding) :
    BaseViewHolder<ChatModel.Image>(binding) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)

    override fun bind(
        item: ChatModel.Image,
        callback: ChatAdapter.OnChatClickListener,
    ): Unit = with(binding) {
        image.load(storage.getReferenceFromUrl(item.url))
    }

    companion object {
        fun create(parent: ViewGroup): ChatImageHolderView {
            val binding = ItemChatImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatImageHolderView(binding)
        }
    }
}
