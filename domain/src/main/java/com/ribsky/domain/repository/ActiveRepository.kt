package com.ribsky.domain.repository

interface ActiveRepository {

    suspend fun getTestScore(): Int

    suspend fun addTestScore(count: Int)

    suspend fun setTestScore(count: Int)

    suspend fun addActiveLesson(id: String)

    suspend fun setActiveLessons(ids: List<String>)

    suspend fun getActiveLessons(): List<String>
    suspend fun addStars(id: String, count: Int)

    suspend fun setStars(map: Map<String, Int>)

    suspend fun getStars(): Map<String, Int>
}
