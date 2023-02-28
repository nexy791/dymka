package com.ribsky.test.adapter.test

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.test.adapter.diff.DiffCallbackTest

class TestAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseWordModel.PickModel, TestViewHolder>(DiffCallbackTest) {

    fun interface OnClickListener {
        fun onClick(isCorrect: Boolean, position: Int)
    }

    fun showAll(position: Int) {
        currentList.forEachIndexed { index, pickModel ->
            if (index != position) {
                pickModel.isShown = true
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder =
        TestViewHolder.create(parent)

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
