package com.ribsky.lessons.adapter.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ribsky.lessons.adapter.diff.DiffCallbackUnit
import com.ribsky.lessons.adapter.notes.holder.NotesViewHolder

class NotesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Unit, NotesViewHolder>(DiffCallbackUnit) {

    fun interface OnClickListener {
        fun onClick(item: Unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder.create(parent)

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
