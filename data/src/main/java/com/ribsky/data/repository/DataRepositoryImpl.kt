package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.repository.*
import java.util.*

class DataRepositoryImpl(
    private val lessonsRepository: LessonRepository,
    private val testRepository: TestRepository,
    private val paragraphRepository: ParagraphRepository,
    private val bestWordRepository: BestWordRepository,
    private val sharedPreferences: SharedPreferences,
    private val topRepository: TopRepository,
    private val internetManager: InternetManager,
) : DataRepository {

    override suspend fun getData(): Result<Unit> = runCatching {
        if (!isNeedUpdate()) {
            val result = loadOffline()
            result.fold(
                onSuccess = {
                    return Result.success(Unit)
                },
                onFailure = { error ->
                    val newResult = loadOnline()
                    newResult.fold(
                        onSuccess = {
                            saveLastTimeUpdate()
                            return Result.success(Unit)
                        },
                        onFailure = {
                            return Result.failure(error)
                        }
                    )
                }
            )
        } else {
            val result = loadOnline()
            result.fold(
                onSuccess = {
                    saveLastTimeUpdate()
                    return Result.success(Unit)
                },
                onFailure = { error ->
                    val newResult = loadOffline()
                    newResult.fold(
                        onSuccess = {
                            return Result.success(Unit)
                        },
                        onFailure = {
                            return Result.failure(error)
                        }
                    )
                }
            )
        }
    }

    private suspend fun loadOnline(): Result<Unit> = runCatching {
        if (internetManager.isOnline()) {
            val lessons = lessonsRepository.loadLessons()
            val words = testRepository.loadBooks()
            val paragraphs = paragraphRepository.loadParagraphs()
            val best = bestWordRepository.loadWords()
            val players = topRepository.loadUsers()
            val result = !lessons.getOrNull().isNullOrEmpty() &&
                !words.getOrNull().isNullOrEmpty() &&
                !paragraphs.getOrNull().isNullOrEmpty() &&
                !best.getOrNull().isNullOrEmpty() &&
                !players.getOrNull().isNullOrEmpty()
            return if (result) {
                Result.success(Unit)
            } else {
                val error: Result<Unit> = Result.failure(
                    lessons.exceptionOrNull() ?: words.exceptionOrNull()
                        ?: paragraphs.exceptionOrNull()
                        ?: best.exceptionOrNull() ?: players.exceptionOrNull()
                        ?: Exception(Exceptions.UnknownException())
                )
                error
            }
        } else {
            return Result.failure(Exceptions.NoInternetException())
        }
    }

    private suspend fun loadOffline(): Result<Unit> = runCatching {
        val lessonStatus = lessonsRepository.isNotEmpty()
        val wordsStatus = testRepository.isNotEmpty()
        val paragraphStatus = paragraphRepository.isNotEmpty()
        val bestWordStatus = bestWordRepository.isNotEmpty()
        val playersStatus = topRepository.isNotEmpty()

        return if (lessonStatus && wordsStatus && paragraphStatus && bestWordStatus && playersStatus) {
            Result.success(Unit)
        } else {
            Result.failure(Exceptions.NoInternetException())
        }
    }

    private fun saveLastTimeUpdate() {
        sharedPreferences.edit {
            putLong(KEY_LAST_UPDATE, Date().time)
        }
    }

    private fun getLastTimeUpdate(): Long = sharedPreferences.getLong(KEY_LAST_UPDATE, 0)
    private fun isNeedUpdate(): Boolean {
        val lastTimeUpdate = getLastTimeUpdate()
        if (lastTimeUpdate <= 0L) return true
        val currentTime = Date().time
        return currentTime - lastTimeUpdate > 21600000 || currentTime < lastTimeUpdate
    }

    companion object {
        const val KEY_LAST_UPDATE = "last_update"
    }
}
