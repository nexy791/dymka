package com.ribsky.domain.usecase.bot

import com.ribsky.domain.repository.BotRepository

interface GetBotScoreForTodayUseCase {
    fun invoke(): Int
}

class GetBotScoreForTodayUseCaseImpl(
    private val botRepository: BotRepository,
) : GetBotScoreForTodayUseCase {

    override fun invoke() = botRepository.getAvailableAnswers()
}
