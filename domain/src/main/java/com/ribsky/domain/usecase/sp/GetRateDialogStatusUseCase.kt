package com.ribsky.domain.usecase.sp

import com.ribsky.domain.repository.SettingsRepository

interface GetRateDialogStatusUseCase {
    fun isShouldShowRateDialog(): Boolean
}

class GetRateDialogStatusUseCaseImpl(
    private val settingsRepository: SettingsRepository,
) : GetRateDialogStatusUseCase {

    override fun isShouldShowRateDialog(): Boolean = settingsRepository.isShouldShowRateDialog
}
