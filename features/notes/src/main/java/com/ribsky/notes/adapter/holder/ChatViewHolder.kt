package com.ribsky.notes.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.notes.adapter.ChatAdapter
import com.ribsky.notes.databinding.ItemChatTextBinding

class ChatViewHolder(private val binding: ItemChatTextBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: BaseNoteModel,
        callback: ChatAdapter.OnChatClickListener,
    ) = with(binding) {
        text.text = item.text.parseAsHtml()
        root.setOnClickListener {
            callback.onTextClick(item.text, item.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ChatViewHolder {
            val binding = ItemChatTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatViewHolder(binding)
        }
    }
}
