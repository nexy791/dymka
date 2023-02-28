package com.ribsky.domain.usecase.clear

import com.ribsky.domain.repository.ClearRepository

interface ClearDataUseCase {

    suspend fun invoke()
}

class ClearDataUseCaseImpl(
    private val clearRepository: ClearRepository,
) : ClearDataUseCase {

    override suspend fun invoke() {
        clearRepository.clear()
    }
}
