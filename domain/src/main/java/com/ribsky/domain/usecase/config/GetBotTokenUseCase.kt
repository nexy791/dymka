package com.ribsky.domain.usecase.config

import com.ribsky.domain.repository.ConfigRepository

interface GetBotTokenUseCase {

    suspend fun invoke(): Result<String>

}

class GetBotTokenUseCaseImpl(
    private val configRepository: ConfigRepository,
) : GetBotTokenUseCase {

    override suspend fun invoke(): Result<String> = configRepository.getBotToken()

}