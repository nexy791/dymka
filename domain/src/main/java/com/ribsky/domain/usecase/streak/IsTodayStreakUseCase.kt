package com.ribsky.domain.usecase.streak

import com.ribsky.domain.repository.StreakRepository

interface IsTodayStreakUseCase {

    fun invoke(): Boolean

}

class IsTodayStreakUseCaseImpl(
    private val streakRepository: StreakRepository,
) : IsTodayStreakUseCase {

    override fun invoke(): Boolean = streakRepository.isTodayStreak()

}
