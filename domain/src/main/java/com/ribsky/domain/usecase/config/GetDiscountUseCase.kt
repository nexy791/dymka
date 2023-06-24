package com.ribsky.domain.usecase.config

import com.ribsky.domain.repository.ConfigRepository

interface GetDiscountUseCase {

    suspend fun invoke(): Result<String>

}

class GetDiscountUseCaseImpl(
    private val configRepository: ConfigRepository,
) : GetDiscountUseCase {

    override suspend fun invoke(): Result<String> = configRepository.getDiscount()

}