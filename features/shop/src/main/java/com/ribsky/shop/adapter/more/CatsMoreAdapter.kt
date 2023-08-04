package com.ribsky.shop.adapter.more

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.shop.adapter.more.diff.MoreCallback

class CatsMoreAdapter(
    private val onMoreClick: () -> Unit,
) : ListAdapter<Unit, СatsViewHolderMore>(MoreCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): СatsViewHolderMore =
        СatsViewHolderMore.create(parent)

    override fun onBindViewHolder(holder: СatsViewHolderMore, position: Int) {
        holder.bind(getItem(position), onMoreClick)
    }
}