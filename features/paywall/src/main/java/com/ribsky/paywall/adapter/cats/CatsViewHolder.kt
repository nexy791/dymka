package com.ribsky.paywall.adapter.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.paywall.databinding.ItemCatsBinding

class CatsViewHolder(private val binding: ItemCatsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: BaseTopModel,
        onCatClick: (BaseTopModel) -> Unit,
    ): Unit = with(binding) {
        root.setOnClickListener {
            onCatClick(item)
        }
        imageView.load(item.image) {
            crossfade(true)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CatsViewHolder {
            val binding = ItemCatsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CatsViewHolder(binding)
        }
    }

}