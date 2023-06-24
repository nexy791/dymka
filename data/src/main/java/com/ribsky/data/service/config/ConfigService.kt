package com.ribsky.data.service.config

interface ConfigService {

    suspend fun getBotToken(): Result<String>

    suspend fun getDiscount(): Result<String>

}