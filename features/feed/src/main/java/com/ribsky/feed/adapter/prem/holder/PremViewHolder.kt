package com.ribsky.feed.adapter.prem.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.feed.adapter.prem.PremAdapter
import com.ribsky.feed.databinding.ItemPremBinding

class PremViewHolder(private val binding: ItemPremBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Boolean, callback: PremAdapter.OnClickListener) = with(binding) {
        btnTrade.setOnClickListener {
            callback.onShop()
        }
        if (item) {
            btnTrade.text = "Керувати"
        } else {
            btnTrade.text = "Отримати"
        }
    }

    companion object {
        fun create(parent: ViewGroup): PremViewHolder {
            val binding = ItemPremBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return PremViewHolder(binding)
        }
    }
}
