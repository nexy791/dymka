package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.model.ActiveApiModel
import com.ribsky.data.model.StarsApiModel
import com.ribsky.data.service.offline.active.ActiveLessonDao
import com.ribsky.data.service.offline.stars.StarsLessonDao
import com.ribsky.domain.repository.ActiveRepository

class ActiveRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val activeLessonDao: ActiveLessonDao,
    private val starsLessonDao: StarsLessonDao,
) : ActiveRepository {

    override suspend fun getTestScore(): Int = sharedPreferences.getInt(ACTIVE_BOOKS, 0)

    override suspend fun addTestScore(count: Int) {
        sharedPreferences.edit { putInt(ACTIVE_BOOKS, getTestScore() + count) }
    }

    override suspend fun setTestScore(count: Int) {
        sharedPreferences.edit { putInt(ACTIVE_BOOKS, count) }
    }

    override suspend fun addActiveLesson(id: String) {
        activeLessonDao.insert(ActiveApiModel(id))
    }

    override suspend fun setActiveLessons(ids: List<String>) {
        activeLessonDao.insert(ids.map { ActiveApiModel(it) })
    }

    override suspend fun getActiveLessons(): List<String> = activeLessonDao.get().map { it.id }
    override suspend fun addStars(id: String, count: Int) {
        if ((starsLessonDao.get(id)?.stars ?: 0) >= count) return
        starsLessonDao.insert(StarsApiModel(id, count))
    }

    override suspend fun setStars(map: Map<String, Int>) {
        starsLessonDao.insert(map.map { StarsApiModel(it.key, it.value) })
    }

    override suspend fun getStars(): Map<String, Int> =
        starsLessonDao.get().associate { it.id to it.stars }

    companion object {
        private const val ACTIVE_BOOKS = "active_books"
    }
}
