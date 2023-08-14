package com.ribsky.domain.usecase.notes

import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.domain.repository.NotesRepository

interface GetNotesUseCase {

    suspend fun invoke(paragraphId: String): List<BaseNoteModel>

}

class GetNotesUseCaseImpl(
    private val notesRepository: NotesRepository,
) : GetNotesUseCase {

    override suspend fun invoke(paragraphId: String) =
        notesRepository.getNotesForParagraph(paragraphId)
}