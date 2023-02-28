package com.ribsky.domain.repository

// TODO: refactor this 
interface SharedRepository {

    val isShouldShowRateDialog: Boolean

    val lastTimeUpdate: String
}
