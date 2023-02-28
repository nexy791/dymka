package com.ribsky.top.adapter.bottom.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.top.databinding.ItemTopBottomBinding

class TopBottomViewHolder(binding: ItemTopBottomBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Unit) {}

    companion object {
        fun create(parent: ViewGroup): TopBottomViewHolder {
            val binding = ItemTopBottomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopBottomViewHolder(binding)
        }
    }
}
