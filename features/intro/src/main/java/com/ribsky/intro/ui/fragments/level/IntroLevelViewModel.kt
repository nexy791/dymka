package com.ribsky.intro.ui.fragments.level

import androidx.lifecycle.ViewModel
import com.ribsky.domain.usecase.bio.GetLevelsBioUseCase
import com.ribsky.domain.usecase.bio.SetLevelBioUseCase

class IntroLevelViewModel(
    private val setLevelBioUseCase: SetLevelBioUseCase,
    private val getLevelsBioUseCase: GetLevelsBioUseCase,
) : ViewModel() {

    fun setLevel(id: Int) {
        setLevelBioUseCase.invoke(id)
    }

    fun getLevels() = getLevelsBioUseCase.invoke()

}