package com.ribsky.domain.usecase.user

import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.repository.AuthRepository
import com.ribsky.domain.repository.UserRepository

interface SyncUserUseCase {

    suspend fun invoke(): Result<BaseUserModel?>
}

class SyncUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : SyncUserUseCase {

    override suspend fun invoke(): Result<BaseUserModel> {
        val token = authRepository.token ?: return Result.failure(Exceptions.AuthException())
        return userRepository.syncUser(token)
    }
}
