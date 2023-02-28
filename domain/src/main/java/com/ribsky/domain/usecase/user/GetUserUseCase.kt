package com.ribsky.domain.usecase.user

import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.repository.AuthRepository
import com.ribsky.domain.repository.UserRepository

interface GetUserUseCase {
    suspend fun invoke(): Result<BaseUserModel>
}

class GetUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : GetUserUseCase {

    override suspend fun invoke(): Result<BaseUserModel> {
        val token = authRepository.token
            ?: return Result.failure(Exceptions.AuthException())
        val user = userRepository.getUserOffline()
            ?: return Result.failure(Exceptions.AuthException())
        return Result.success(user)
    }
}
