package com.ribsky.lesson.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.lesson.model.ChatModel

object DiffCallbackTestPick : DiffUtil.ItemCallback<ChatModel.Test.TestModel>() {
    override fun areItemsTheSame(
        oldItem: ChatModel.Test.TestModel,
        newItem: ChatModel.Test.TestModel,
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ChatModel.Test.TestModel,
        newItem: ChatModel.Test.TestModel,
    ): Boolean =
        oldItem.text == newItem.text
}
