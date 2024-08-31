package com.ribsky.shop.adapter.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.shop.databinding.ItemCatsBinding

class CatsViewHolder(private val binding: ItemCatsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: BaseTopModel,
        onCatClick: (BaseTopModel) -> Unit,
    ): Unit = with(binding) {
        root.setOnClickListener {
            onCatClick(item)
        }
        imageView.loadImage(item.image)
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