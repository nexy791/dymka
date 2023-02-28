package com.ribsky.lesson.adapter.test

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.lesson.adapter.diff.DiffCallbackTestPick
import com.ribsky.lesson.model.ChatModel

class TestPickAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ChatModel.Test.TestModel, TestPickHolder>(DiffCallbackTestPick) {

    fun interface OnClickListener {
        fun onClick(item: ChatModel.Test.TestModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestPickHolder =
        TestPickHolder.create(parent)

    override fun onBindViewHolder(holder: TestPickHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
