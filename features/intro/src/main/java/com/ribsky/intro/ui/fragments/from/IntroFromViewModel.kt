package com.ribsky.intro.ui.fragments.from

import androidx.lifecycle.ViewModel
import com.ribsky.domain.usecase.bio.GetFromsBioUseCase
import com.ribsky.domain.usecase.bio.SetFromBioUseCase

class IntroFromViewModel(
    private val setFromBioUseCase: SetFromBioUseCase,
    private val getFromsBioUseCase: GetFromsBioUseCase,
) : ViewModel() {

    fun setFrom(id: Int) {
        setFromBioUseCase.invoke(id)
    }

    fun getFroms() = getFromsBioUseCase.invoke()

}