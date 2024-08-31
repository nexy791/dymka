package com.ribsky.data.service.config

import com.ribsky.data.model.PromoApiModel

interface ConfigService {

    suspend fun getBotToken(): Result<String>

    suspend fun getDiscount(): Result<String>

    suspend fun getPromo(): Result<PromoApiModel>

}