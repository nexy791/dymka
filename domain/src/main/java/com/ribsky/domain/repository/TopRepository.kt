package com.ribsky.domain.repository

import com.ribsky.domain.model.top.BaseTopModel

interface TopRepository {

    suspend fun loadUsers(): Result<List<BaseTopModel>>

    suspend fun getUsersByScore(): List<BaseTopModel>

    suspend fun getUser(id: Int): BaseTopModel

    suspend fun isNotEmpty(): Boolean
    suspend fun getUsersByStreak(): List<BaseTopModel>
    suspend fun getUsersByPremium(): List<BaseTopModel>
    suspend fun getUsersByStars(): List<BaseTopModel>

    suspend fun getUsersForUpByTests(): List<BaseTopModel>
    suspend fun getUsersForUpByStars(): List<BaseTopModel>

    suspend fun getUsersForMoreUpByTests(): List<BaseTopModel>
    suspend fun getUsersForMoreUpByStars(): List<BaseTopModel>

    suspend fun getUsersForDownByTests(): List<BaseTopModel>

    suspend fun getUsersForDownByStars(): List<BaseTopModel>


}
