package com.ribsky.feed.adapter.word.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.feed.adapter.word.BestWordAdapter
import com.ribsky.feed.databinding.ItemBestWordBinding

class BestWordViewHolder(private val binding: ItemBestWordBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BaseBestWordModel, callback: BestWordAdapter.OnClickListener) = with(binding) {
        btnNext.setOnClickListener {
            callback.onClick(item.id)
        }
        materialCardView.setOnClickListener {
            callback.onClick(item.id)
        }
        tvTitle.text = item.title
        tvDescription.text = item.description
    }

    companion object {
        fun create(parent: ViewGroup): BestWordViewHolder {
            val binding = ItemBestWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BestWordViewHolder(binding)
        }
    }
}
