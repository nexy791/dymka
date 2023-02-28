package com.ribsky.settings.adapter.library

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.settings.adapter.diff.DiffCallbackLibrary
import com.ribsky.settings.adapter.library.holder.LibraryViewHolder
import com.ribsky.settings.model.LibraryModel

class LibraryAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<LibraryModel, LibraryViewHolder>(DiffCallbackLibrary) {

    fun interface OnClickListener {
        fun onClick(model: LibraryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder =
        LibraryViewHolder.create(parent)

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
