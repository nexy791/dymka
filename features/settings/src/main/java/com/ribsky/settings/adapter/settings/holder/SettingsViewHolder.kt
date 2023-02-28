package com.ribsky.settings.adapter.settings.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.settings.adapter.settings.SettingsAdapter
import com.ribsky.settings.databinding.ItemSettingsBinding
import com.ribsky.settings.model.Settings

class SettingsViewHolder(private val binding: ItemSettingsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Settings.SettingsModel,
        onClickListener: SettingsAdapter.OnClickListener,
    ) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick(item.type)
        }
        tvTitle.text = item.title
        tvIcon.setImageResource(item.drawable)
    }

    companion object {
        fun create(parent: ViewGroup): SettingsViewHolder {
            val binding = ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SettingsViewHolder(binding)
        }
    }
}
