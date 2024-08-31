package com.ribsky.data.repository

import com.ribsky.data.mapper.promo.PromoMapper
import com.ribsky.data.service.config.ConfigService
import com.ribsky.domain.model.promo.BasePromoModel
import com.ribsky.domain.repository.ConfigRepository

class ConfigRepositoryImpl(
    private val configService: ConfigService,
    private val promoMapper: PromoMapper
) : ConfigRepository {

    override suspend fun getBotToken(): Result<String> =
        configService.getBotToken()

    override suspend fun getDiscount(): Result<String> =
        configService.getDiscount()

    override suspend fun getPromo(): Result<BasePromoModel> =
        configService.getPromo().map(promoMapper::map)
}