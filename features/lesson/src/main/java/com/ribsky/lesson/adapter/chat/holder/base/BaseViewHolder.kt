package com.ribsky.lesson.adapter.chat.holder.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ribsky.lesson.adapter.chat.ChatAdapter

abstract class BaseViewHolder<in Model>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var photoUrl: String = ""
    fun setUserPhoto(photoUrl: String) {
        this.photoUrl = photoUrl
    }

    abstract fun bind(item: Model, callback: ChatAdapter.OnChatClickListener)
}
