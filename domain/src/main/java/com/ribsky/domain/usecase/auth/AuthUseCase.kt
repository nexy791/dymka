package com.ribsky.domain.usecase.auth

import com.ribsky.domain.repository.AuthRepository

interface AuthUseCase {

    suspend fun invoke(token: String): Result<Unit>
}

class AuthUseCaseImpl(
    private val authRepository: AuthRepository,
) : AuthUseCase {

    override suspend fun invoke(token: String): Result<Unit> = authRepository.authUser(token)
}
