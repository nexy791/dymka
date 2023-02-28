package com.ribsky.domain.usecase.data

import com.ribsky.domain.repository.DataRepository

interface LoadDataUseCase {

    suspend fun invoke(): Result<Unit>
}

class LoadDataUseCaseImpl(
    private val dataRepository: DataRepository,
) : LoadDataUseCase {

    override suspend fun invoke(): Result<Unit> = dataRepository.getData()
}
