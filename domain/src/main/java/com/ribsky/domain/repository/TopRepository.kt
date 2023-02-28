package com.ribsky.domain.repository

import com.ribsky.domain.model.top.BaseTopModel

interface TopRepository {

    suspend fun loadUsers(): Result<List<BaseTopModel>>

    suspend fun getUsersByScore(): List<BaseTopModel>

    suspend fun getUsersByLessons(): List<BaseTopModel>

    suspend fun getUser(id: Int): BaseTopModel

    suspend fun isNotEmpty(): Boolean
}
