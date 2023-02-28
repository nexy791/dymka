package com.ribsky.domain.usecase.auth

import com.ribsky.domain.repository.AuthRepository

interface SignOutUseCase {
    suspend fun invoke()
}

class SignOutUseCaseImpl(
    private val authRepository: AuthRepository,
) : SignOutUseCase {
    override suspend fun invoke() {
        authRepository.signOut()
    }
}
