package com.ribsky.lessons.adapter.notes.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.lessons.adapter.notes.NotesAdapter
import com.ribsky.lessons.databinding.ItemNotesBinding

class NotesViewHolder(private val binding: ItemNotesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Unit, onClickListener: NotesAdapter.OnClickListener) =
        with(binding) {
            root.setOnClickListener {
                onClickListener.onClick(item)
            }
        }

    companion object {
        fun create(parent: ViewGroup): NotesViewHolder {
            val binding = ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NotesViewHolder(binding)
        }
    }
}
