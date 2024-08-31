package com.ribsky.tests.adapter.header

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.tests.adapter.diff.DiffCallbackTestHeader

class TestHeaderAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Int, TestHeaderViewHolder>(DiffCallbackTestHeader) {

    interface OnClickListener {
        fun onGameClick()
        fun onScoreInfoClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHeaderViewHolder =
        TestHeaderViewHolder.create(parent)

    override fun onBindViewHolder(holder: TestHeaderViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
