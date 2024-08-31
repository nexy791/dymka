package com.ribsky.data.repository

import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.data.service.offline.time.TimeService
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DataRepositoryImpl(
    private val lessonsRepository: LessonRepository,
    private val testRepository: TestRepository,
    private val paragraphRepository: ParagraphRepository,
    private val bestWordRepository: BestWordRepository,
    private val timeService: TimeService,
    private val topRepository: TopRepository,
    private val articleRepository: ArticleRepository,
    private val internetManager: InternetManager,
) : DataRepository {

    override suspend fun getData(): Result<Unit> = runCatching {
        if (!timeService.isNeedUpdate()) {
            val result = loadOffline()
            result.fold(
                onSuccess = {
                    return Result.success(Unit)
                },
                onFailure = { error ->
                    val newResult = loadOnline()
                    newResult.fold(
                        onSuccess = {
                            timeService.saveLastTimeUpdate()
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
                    timeService.saveLastTimeUpdate()
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

    private suspend fun loadOnline(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            if (internetManager.isOnline()) {
                val lessons = async { lessonsRepository.loadLessons() }
                val words = async { testRepository.loadBooks() }
                val paragraphs = async { paragraphRepository.loadParagraphs() }
                val best = async { bestWordRepository.loadWords() }
                val players = async { topRepository.loadUsers() }
                val articles = async { articleRepository.loadArticles() }

                val result = !lessons.await().getOrNull().isNullOrEmpty() &&
                        !words.await().getOrNull().isNullOrEmpty() &&
                        !paragraphs.await().getOrNull().isNullOrEmpty() &&
                        !best.await().getOrNull().isNullOrEmpty() &&
                        !players.await().getOrNull().isNullOrEmpty() &&
                        !articles.await().getOrNull().isNullOrEmpty()
                if (result) {
                    Result.success(Unit)
                } else {
                    val error: Result<Unit> = Result.failure(
                        lessons.await().exceptionOrNull() ?: words.await().exceptionOrNull()
                        ?: paragraphs.await().exceptionOrNull()
                        ?: best.await().exceptionOrNull() ?: players.await().exceptionOrNull()
                        ?: articles.await().exceptionOrNull() ?: Exception(Exceptions.UnknownException())
                    )
                    error
                }
            } else {
                Result.failure(Exceptions.NoInternetException())
            }
        }.getOrNull() ?: Result.failure(Exceptions.UnknownException())
    }

    private suspend fun loadOffline(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val lessonStatus = async { lessonsRepository.isNotEmpty() }
            val wordsStatus = async { testRepository.isNotEmpty() }
            val paragraphStatus = async { paragraphRepository.isNotEmpty() }
            val bestWordStatus = async { bestWordRepository.isNotEmpty() }
            val playersStatus = async { topRepository.isNotEmpty() }
            val articlesStatus = async { articleRepository.isNotEmpty() }

             if (lessonStatus.await() && wordsStatus.await() && paragraphStatus.await() && bestWordStatus.await() && playersStatus.await() && articlesStatus.await()) {
                Result.success(Unit)
            } else {
                Result.failure(Exceptions.NoInternetException())
            }
        }.getOrNull() ?: Result.failure(Exceptions.UnknownException())
    }
}
