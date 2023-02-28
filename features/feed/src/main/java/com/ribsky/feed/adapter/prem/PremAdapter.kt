package com.ribsky.feed.adapter.prem

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.feed.adapter.diff.DiffCallbackPrem
import com.ribsky.feed.adapter.prem.holder.PremViewHolder

class PremAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Boolean, PremViewHolder>(DiffCallbackPrem) {

    fun interface OnClickListener {
        fun onShop()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremViewHolder =
        PremViewHolder.create(parent)

    override fun onBindViewHolder(holder: PremViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
