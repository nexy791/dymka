package com.ribsky.domain.usecase.bot

import com.ribsky.domain.repository.BotRepository

interface AddBotScoreUseCase {
    fun invoke()
}

class AddBotScoreUseCaseImpl(
    private val botRepository: BotRepository,
) : AddBotScoreUseCase {

    override fun invoke() {
        botRepository.incrementAnswers()
    }
}
