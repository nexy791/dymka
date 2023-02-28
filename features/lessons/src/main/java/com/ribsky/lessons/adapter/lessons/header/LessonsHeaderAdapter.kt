package com.ribsky.lessons.adapter.lessons.header

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.lessons.adapter.diff.DiffCallbackFeed
import com.ribsky.lessons.adapter.lessons.header.holder.HeaderViewHolder

class LessonsHeaderAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseParagraphModel, HeaderViewHolder>(DiffCallbackFeed) {

    fun interface OnClickListener {
        fun onClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder =
        HeaderViewHolder.create(parent)

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
