package com.ribsky.top.adapter.bottom

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.top.adapter.bottom.holder.TopBottomViewHolder
import com.ribsky.top.adapter.diff.DiffCallbackEmpty

class TopBottomAdapter :
    ListAdapter<Unit, TopBottomViewHolder>(DiffCallbackEmpty) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopBottomViewHolder =
        TopBottomViewHolder.create(parent)

    override fun onBindViewHolder(holder: TopBottomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
