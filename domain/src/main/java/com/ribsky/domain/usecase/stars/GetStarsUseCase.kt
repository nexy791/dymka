package com.ribsky.domain.usecase.stars

import com.ribsky.domain.repository.ActiveRepository

interface GetStarsUseCase {

    suspend fun invoke(): Int

}

class GetStarsUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : GetStarsUseCase {

    override suspend fun invoke() = activeRepository.getStars().values.sum()

}
