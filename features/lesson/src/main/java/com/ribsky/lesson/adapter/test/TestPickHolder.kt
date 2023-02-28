package com.ribsky.lesson.adapter.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.lesson.databinding.ItemChatTestPickBinding
import com.ribsky.lesson.model.ChatModel

class TestPickHolder(private val binding: ItemChatTestPickBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ChatModel.Test.TestModel, onClickListener: TestPickAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener {
                onClickListener.onClick(item, bindingAdapterPosition)
            }
            tvTitle.text = item.text.parseAsHtml()
        }

    companion object {
        fun create(parent: ViewGroup): TestPickHolder {
            val binding = ItemChatTestPickBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TestPickHolder(binding)
        }
    }
}
