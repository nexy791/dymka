package com.ribsky.data.repository

import com.ribsky.data.service.config.ConfigService
import com.ribsky.domain.repository.ConfigRepository

class ConfigRepositoryImpl(
    private val configService: ConfigService
) : ConfigRepository {

    override suspend fun getBotToken(): Result<String> =
        configService.getBotToken()

    override suspend fun getDiscount(): Result<String> =
        configService.getDiscount()
}