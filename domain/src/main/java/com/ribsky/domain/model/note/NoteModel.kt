package com.ribsky.domain.model.note

data class NoteModel(
    override val id: Int,
    override val text: String,
    override val paragraphId: String,
) : BaseNoteModel
