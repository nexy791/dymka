package com.ribsky.domain.usecase.active

import com.ribsky.domain.repository.ActiveRepository

interface AddActiveLessonUseCase {

    suspend fun invoke(id: String)
}

class AddActiveLessonUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : AddActiveLessonUseCase {
    override suspend fun invoke(id: String) {
        activeRepository.addActiveLesson(id)
    }
}
