package com.ribsky.data.repository

import com.ribsky.billing.manager.SubManager
import com.ribsky.data.mapper.note.NoteMapper
import com.ribsky.data.model.NoteApiModel
import com.ribsky.data.service.offline.notes.NotesDao
import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.domain.repository.NotesRepository

class NotesRepositoryImpl(
    private val notesDao: NotesDao,
    private val noteMapper: NoteMapper,
    private val subManager: SubManager,
) : NotesRepository {
    override suspend fun addNote(note: String, paragraphId: String): Boolean {
        if (notesDao.get(paragraphId).size >= LIMIT_FOR_NOTES && !subManager.isSub()) return false
        notesDao.insert(NoteApiModel(note, paragraphId))
        return true
    }

    override suspend fun getNotesForParagraph(paragraphId: String): List<BaseNoteModel> =
        notesDao.get(paragraphId).map { noteMapper.map(it) }

    override suspend fun deleteNote(id: Int) {
        notesDao.delete(id)
    }

    companion object {
        private const val LIMIT_FOR_NOTES = 25
    }
}