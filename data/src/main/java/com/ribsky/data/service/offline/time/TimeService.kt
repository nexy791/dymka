package com.ribsky.data.service.offline.time

interface TimeService {

    fun isNeedUpdate(): Boolean
    fun saveLastTimeUpdate()
    fun getLastTimeUpdate(): Long

}