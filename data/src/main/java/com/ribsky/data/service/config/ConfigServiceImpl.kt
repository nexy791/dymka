package com.ribsky.data.service.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

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
                    ?: Throwable("Помилка отримання токену\nЯкщо помилка повторюється, спробуйте перевстановити застосунок")
            )
        }
    }

    override suspend fun getDiscount(): Result<String> {
        val result = activate()
        return if (isActivated) {
            val time = runCatching { formatTimeDDMMMM(discount!!) }
            if (time.isSuccess) {
                if (discount!! >= Date().time) {
                    Result.success(time.getOrNull()!!)
                } else {
                    Result.failure(time.exceptionOrNull() ?: Throwable("Помилка"))
                }
            } else {
                Result.failure(time.exceptionOrNull() ?: Throwable("Помилка"))
            }
        } else {
            Result.failure(
                result.exceptionOrNull()
                    ?: Throwable("Помилка отримання знижки\nЯкщо помилка повторюється, спробуйте перевстановити застосунок")
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

    private fun formatTimeDDMMMM(time: Long): String? {
        return try {
            val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("uk"))
            formatter.format(time)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val KEY_BOT = "api_key"
        private const val KEY_DISCOUNT = "discount"
    }
}