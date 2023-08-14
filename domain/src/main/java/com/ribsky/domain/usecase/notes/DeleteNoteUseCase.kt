package com.ribsky.domain.usecase.notes

import com.ribsky.domain.repository.NotesRepository

interface DeleteNoteUseCase {

    suspend fun invoke(id: Int)

}

class DeleteNoteUseCaseImpl(
    private val notesRepository: NotesRepository,
) : DeleteNoteUseCase {

    override suspend fun invoke(id: Int) = notesRepository.deleteNote(
        id = id
    )

}