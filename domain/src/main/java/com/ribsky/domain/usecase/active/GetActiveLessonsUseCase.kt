package com.ribsky.domain.usecase.active

import com.ribsky.domain.repository.ActiveRepository

interface GetActiveLessonsUseCase {

    suspend fun invoke(): List<String>
}

class GetActiveLessonsUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : GetActiveLessonsUseCase {

    override suspend fun invoke(): List<String> =
        activeRepository.getActiveLessons()
}
