package com.ribsky.domain.repository

import com.ribsky.domain.model.note.BaseNoteModel

interface NotesRepository {

    suspend fun addNote(note: String, paragraphId: String): Boolean

    suspend fun getNotesForParagraph(paragraphId: String): List<BaseNoteModel>

    suspend fun deleteNote(id: Int)

}