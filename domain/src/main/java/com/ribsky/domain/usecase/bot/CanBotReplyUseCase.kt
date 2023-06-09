package com.ribsky.domain.usecase.bot

import com.ribsky.domain.repository.BotRepository

interface CanBotReplyUseCase {
    fun invoke(): Boolean
}

class CanBotReplyUseCaseImpl(
    private val botRepository: BotRepository,
) : CanBotReplyUseCase {

    override fun invoke() = botRepository.canBotAnswer()
}
