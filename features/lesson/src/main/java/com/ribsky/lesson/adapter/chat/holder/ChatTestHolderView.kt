package com.ribsky.lesson.adapter.chat.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.adapter.chat.holder.base.BaseViewHolder
import com.ribsky.lesson.adapter.test.TestPickAdapter
import com.ribsky.lesson.databinding.ItemChatTestBinding
import com.ribsky.lesson.model.ChatModel

class ChatTestHolderView(private val binding: ItemChatTestBinding) :
    BaseViewHolder<ChatModel.Test>(binding) {

    override fun bind(
        item: ChatModel.Test,
        callback: ChatAdapter.OnChatClickListener,
    ): Unit = with(binding) {
        textView.text = item.text.parseAsHtml()

        val mAdapter = TestPickAdapter { i, _ ->
            if (item.isActive) callback.onTestClick(i)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = mAdapter.apply {
                submitList(item.testModel)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ChatTestHolderView {
            val binding = ItemChatTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatTestHolderView(binding)
        }
    }
}
