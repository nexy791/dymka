package com.ribsky.domain.usecase.streak

import com.ribsky.domain.repository.StreakRepository

interface SetTodayStreakUseCase {

    fun invoke()

}

class SetTodayStreakUseCaseImpl(
    private val streakRepository: StreakRepository,
) : SetTodayStreakUseCase {

    override fun invoke() {
        streakRepository.setTodayStreak()
    }
}
