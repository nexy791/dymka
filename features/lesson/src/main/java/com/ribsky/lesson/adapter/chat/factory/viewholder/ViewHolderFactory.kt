package com.ribsky.lesson.adapter.chat.factory.viewholder

import android.view.ViewGroup
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.model.ChatModel

interface ViewHolderFactory {

    fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatModel>
}
