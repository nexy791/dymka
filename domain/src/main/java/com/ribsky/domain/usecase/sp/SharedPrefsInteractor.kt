package com.ribsky.domain.usecase.sp

import com.ribsky.domain.repository.SharedRepository

// TODO: refactor this
interface SharedPrefsInteractor {

    fun isShouldShowRateDialog(): Boolean

    fun getLastTimeUpdate(): String
}

class SharedPrefsInteractorImpl(
    private val sharedRepository: SharedRepository,
) : SharedPrefsInteractor {

    override fun isShouldShowRateDialog(): Boolean = sharedRepository.isShouldShowRateDialog
    override fun getLastTimeUpdate(): String = sharedRepository.lastTimeUpdate
}
