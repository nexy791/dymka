package com.ribsky.settings.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.settings.model.LibraryModel

object DiffCallbackLibrary : DiffUtil.ItemCallback<LibraryModel>() {
    override fun areItemsTheSame(oldItem: LibraryModel, newItem: LibraryModel): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: LibraryModel, newItem: LibraryModel): Boolean =
        oldItem.url == newItem.url
}
