package com.ribsky.intro.ui

import androidx.lifecycle.ViewModel
import com.ribsky.domain.usecase.bio.GetCurrentFromBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentGoalBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentLevelBioUseCase

class IntroViewModel(
    private val getCurrentFromBioUseCase: GetCurrentFromBioUseCase,
    private val getCurrentLevelBioUseCase: GetCurrentLevelBioUseCase,
    private val getCurrentGoalBioUseCase: GetCurrentGoalBioUseCase,
) : ViewModel() {

    fun getFrom() = getCurrentFromBioUseCase.invoke()

    fun getLevel() = getCurrentLevelBioUseCase.invoke()

    fun getGoal() = getCurrentGoalBioUseCase.invoke()
}