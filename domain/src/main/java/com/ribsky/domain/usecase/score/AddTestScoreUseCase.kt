package com.ribsky.domain.usecase.score

import com.ribsky.domain.repository.ActiveRepository

interface AddTestScoreUseCase {
    suspend fun invoke(count: Int)
}

class AddTestScoreUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : AddTestScoreUseCase {
    override suspend fun invoke(count: Int) {
        activeRepository.addTestScore(count)
    }
}
