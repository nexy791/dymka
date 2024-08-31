package com.ribsky.domain.repository

import com.ribsky.domain.model.promo.BasePromoModel

interface ConfigRepository {

    suspend fun getBotToken(): Result<String>

    suspend fun getDiscount(): Result<String>

    suspend fun getPromo(): Result<BasePromoModel>


}