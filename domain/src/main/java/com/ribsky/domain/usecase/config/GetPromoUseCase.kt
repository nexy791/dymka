package com.ribsky.domain.usecase.config

import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.domain.repository.ConfigRepository

interface GetPromoUseCase {

    suspend fun invoke(): Result<BasePromoModel>

}

class GetPromoUseCaseImpl(
    private val configRepository: ConfigRepository,
) : GetPromoUseCase {

    override suspend fun invoke(): Result<BasePromoModel> = configRepository.getPromo()

}