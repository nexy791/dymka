package com.ribsky.settings.adapter.settings.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.settings.databinding.ItemLabelBinding
import com.ribsky.settings.model.Settings

class LabelViewHolder(private val binding: ItemLabelBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Settings.LabelModel) = with(binding) {
        tvTitle.text = item.label
    }

    companion object {
        fun create(parent: ViewGroup): LabelViewHolder {
            val binding = ItemLabelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LabelViewHolder(binding)
        }
    }
}
