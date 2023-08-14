package com.ribsky.notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.notes.adapter.diff.DiffCallbackChat
import com.ribsky.notes.adapter.holder.ChatViewHolder

class ChatAdapter(
    private val callback: OnChatClickListener,
) : ListAdapter<BaseNoteModel, ChatViewHolder>(DiffCallbackChat) {


    interface OnChatClickListener {
        fun onTextClick(text: String, id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder =
        ChatViewHolder.create(parent)

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = with(holder) {
        bind(getItem(position), callback)
    }

}
