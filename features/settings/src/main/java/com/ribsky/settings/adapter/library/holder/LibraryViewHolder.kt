package com.ribsky.settings.adapter.library.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.settings.adapter.library.LibraryAdapter
import com.ribsky.settings.databinding.ItemLibraryBinding
import com.ribsky.settings.model.LibraryModel

class LibraryViewHolder(private val binding: ItemLibraryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LibraryModel, onClickListener: LibraryAdapter.OnClickListener) = with(binding) {
        root.setOnClickListener {
            onClickListener.onClick(item)
        }
        tvName.text = item.name
        tvDescription.text = item.description
    }

    companion object {
        fun create(parent: ViewGroup): LibraryViewHolder {
            val binding = ItemLibraryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LibraryViewHolder(binding)
        }
    }
}
