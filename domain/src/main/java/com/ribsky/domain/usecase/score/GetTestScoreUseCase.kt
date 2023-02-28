package com.ribsky.domain.usecase.score

import com.ribsky.domain.repository.ActiveRepository

interface GetTestScoreUseCase {

    suspend fun invoke(): Int
}

class GetTestScoreUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : GetTestScoreUseCase {

    override suspend fun invoke(): Int = activeRepository.getTestScore()
}
