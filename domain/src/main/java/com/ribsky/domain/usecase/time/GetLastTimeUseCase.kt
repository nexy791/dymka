package com.ribsky.domain.usecase.time

import com.ribsky.domain.repository.SettingsRepository

interface GetLastTimeUseCase {

    fun invoke(): Long

}

class GetLastTimeUseCaseImpl(
    private val settingsRepository: SettingsRepository,
) : GetLastTimeUseCase {

    override fun invoke(): Long = settingsRepository.lastTimeUpdate

}