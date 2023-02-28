package com.ribsky.domain.repository

interface AuthRepository {

    val token: String?

    suspend fun authUser(token: String): Result<Unit>

    suspend fun isAuth(): Boolean

    fun signOut()
}
