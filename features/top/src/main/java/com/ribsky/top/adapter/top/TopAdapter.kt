package com.ribsky.top.adapter.top

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.top.adapter.diff.DiffCallbackTop
import com.ribsky.top.adapter.top.holder.TopViewHolder
import com.ribsky.top.model.TopModel

class TopAdapter(
    private val onClickListener: OnClickListener,
) :
    ListAdapter<TopModel, TopViewHolder>(DiffCallbackTop) {

    fun interface OnClickListener {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder =
        TopViewHolder.create(parent)

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.type
}
