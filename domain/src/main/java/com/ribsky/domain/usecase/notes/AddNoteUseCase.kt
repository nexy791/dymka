package com.ribsky.domain.usecase.notes

import com.ribsky.domain.repository.NotesRepository

interface AddNoteUseCase {

    suspend fun invoke(note: String, paragraphId: String): Boolean

}

class AddNoteUseCaseImpl(
    private val notesRepository: NotesRepository,
) : AddNoteUseCase {

    override suspend fun invoke(note: String, paragraphId: String) =
        notesRepository.addNote(note, paragraphId)
}