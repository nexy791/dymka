package com.ribsky.notes.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.note.BaseNoteModel

object DiffCallbackChat : DiffUtil.ItemCallback<BaseNoteModel>() {
    override fun areItemsTheSame(oldItem: BaseNoteModel, newItem: BaseNoteModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BaseNoteModel, newItem: BaseNoteModel): Boolean =
        oldItem.id == newItem.id
}
