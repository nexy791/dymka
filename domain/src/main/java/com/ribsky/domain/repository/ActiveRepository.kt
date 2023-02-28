package com.ribsky.domain.repository

interface ActiveRepository {

    suspend fun getTestScore(): Int

    suspend fun addTestScore(count: Int)

    suspend fun setTestScore(count: Int)

    suspend fun addActiveLesson(id: String)

    suspend fun setActiveLessons(ids: List<String>)

    suspend fun getActiveLessons(): List<String>
}
