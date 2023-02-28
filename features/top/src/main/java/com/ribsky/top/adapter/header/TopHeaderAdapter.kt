package com.ribsky.top.adapter.header

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.top.adapter.diff.DiffCallbackTop
import com.ribsky.top.adapter.header.holder.TopHeaderViewHolder
import com.ribsky.top.model.TopModel
import com.ribsky.top.ui.base.ViewType

// TODO: refactor topModel as sealed
class TopHeaderAdapter(
    private val type: ViewType,
    private val time: String,
    private val onClickListener: OnClickListener,
) :
    ListAdapter<TopModel, TopHeaderViewHolder>(DiffCallbackTop) {

    fun interface OnClickListener {
        fun onClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeaderViewHolder =
        TopHeaderViewHolder.create(parent)

    override fun onBindViewHolder(holder: TopHeaderViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener, type, time)
    }
}
