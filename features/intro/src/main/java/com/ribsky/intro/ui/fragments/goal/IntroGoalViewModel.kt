package com.ribsky.intro.ui.fragments.goal

import androidx.lifecycle.ViewModel
import com.ribsky.domain.usecase.bio.GetGoalsBioUseCase
import com.ribsky.domain.usecase.bio.SetGoalBioUseCase

class IntroGoalViewModel(
    private val setGoalBioUseCase: SetGoalBioUseCase,
    private val getGoalsBioUseCase: GetGoalsBioUseCase,
) : ViewModel() {

    fun setGoal(id: Int) {
        setGoalBioUseCase.invoke(id)
    }

    fun getGoals() = getGoalsBioUseCase.invoke()

}