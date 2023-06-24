package com.ribsky.domain.usecase.streak

import com.ribsky.domain.repository.StreakRepository

interface GetCurrentStreakUseCase {

    fun invoke(): Int

}

class GetCurrentStreakUseCaseImpl(
    private val streakRepository: StreakRepository
) : GetCurrentStreakUseCase {

    override fun invoke(): Int = streakRepository.getCurrentStreak()

}