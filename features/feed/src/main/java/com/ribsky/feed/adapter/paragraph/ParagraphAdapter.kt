package com.ribsky.feed.adapter.paragraph

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.feed.adapter.diff.DiffCallbackParagraph
import com.ribsky.feed.adapter.paragraph.holder.ParagraphViewHolder

class ParagraphAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BaseParagraphModel, ParagraphViewHolder>(DiffCallbackParagraph) {

    fun interface OnClickListener {
        fun onClick(model: BaseParagraphModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder =
        ParagraphViewHolder.create(parent)

    override fun onBindViewHolder(holder: ParagraphViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
