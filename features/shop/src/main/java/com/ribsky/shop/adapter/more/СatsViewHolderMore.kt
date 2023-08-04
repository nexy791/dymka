package com.ribsky.shop.adapter.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.shop.databinding.ItemCatsMoreBinding

class СatsViewHolderMore(private val binding: ItemCatsMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Unit,
        onMoreClick: () -> Unit,
    ): Unit = with(binding) {
        root.setOnClickListener {
            onMoreClick()
        }
    }

    companion object {
        fun create(parent: ViewGroup): СatsViewHolderMore {
            val binding = ItemCatsMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return СatsViewHolderMore(binding)
        }
    }

}