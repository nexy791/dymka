package com.ribsky.data.service.config

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ribsky.core.utils.DateUtils.Companion.formatDateDDMMMMYYYY
import kotlinx.coroutines.tasks.await
import java.util.Date

class ConfigServiceImpl(
    private val remoteConfig: FirebaseRemoteConfig,
) : ConfigService {

    private var isActivated = false

    private var token: String? = null
    private var discount: Long? = null


    override suspend fun getBotToken(): Result<String> {
        val result = activate()
        return if (isActivated) {
            Result.success(token!!)
        } else {
            Result.failure(
                result.exceptionOrNull()
                    ?: Throwable("Помилка отримання токену\nЯкщо помилка повторюється, спробуй перевстановити застосунок")
            )
        }
    }

    override suspend fun getDiscount(): Result<String> {
        val result = activate()
        return if (isActivated) {
            val time = formatDateDDMMMMYYYY(discount!!)
            if (time != null) {
                if (discount!! >= Date().time) {
                    Result.success(time)
                } else {
                    Result.failure(Throwable("Помилка"))
                }
            } else {
                Result.failure(Throwable("Помилка"))
            }
        } else {
            Result.failure(
                result.exceptionOrNull()
                    ?: Throwable("Помилка отримання знижки\nЯкщо помилка повторюється, спробуй перевстановити застосунок")
            )
        }
    }

    private suspend fun activate() = runCatching {
        if (isActivated) return@runCatching
        remoteConfig.fetchAndActivate().await()
        token = remoteConfig.getString(KEY_BOT)
        discount = remoteConfig.getLong(KEY_DISCOUNT)
        if (token?.isNotEmpty() == true && (discount ?: 0) > 0) {
            isActivated = true
        } else {
            remoteConfig.reset().await()
            error("Помилка отримання конфігурації\nЯкщо помилка повторюється, спробуйте перевстановити застосунок")
        }
    }


    companion object {
        const val KEY_BOT = "api_key"
        const val KEY_DISCOUNT = "discount"
    }
}