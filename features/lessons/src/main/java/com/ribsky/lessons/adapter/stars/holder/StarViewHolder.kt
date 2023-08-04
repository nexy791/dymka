package com.ribsky.lessons.adapter.stars.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.lessons.adapter.stars.StarAdapter
import com.ribsky.lessons.databinding.ItemStarsBinding
import com.ribsky.lessons.model.StarModel

class StarViewHolder(private val binding: ItemStarsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StarModel, onClickListener: StarAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener {
                onClickListener.onClick(item)
            }
            btnTrade.setOnClickListener {
                onClickListener.onClick(item)
            }
            tvCoins.text = "${item.stars}/${item.allStars}"
        }

    companion object {
        fun create(parent: ViewGroup): StarViewHolder {
            val binding = ItemStarsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return StarViewHolder(binding)
        }
    }
}
