package com.ribsky.domain.repository

interface ConfigRepository {

    suspend fun getBotToken(): Result<String>

    suspend fun getDiscount(): Result<String>


}