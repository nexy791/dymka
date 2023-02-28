package com.ribsky.tests.adapter.test

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.tests.adapter.diff.DiffCallbackTest
import com.ribsky.tests.model.TestModel

class TestAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TestModel, TestViewHolder>(DiffCallbackTest) {

    fun interface OnClickListener {
        fun onClick(test: TestModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder.create(parent)

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
