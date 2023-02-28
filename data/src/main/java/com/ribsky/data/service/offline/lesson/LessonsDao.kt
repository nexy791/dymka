package com.ribsky.data.service.offline.lesson

import androidx.room.*
import com.ribsky.data.model.LessonApiModel

@Dao
interface LessonsDao {

    @Transaction
    suspend fun insert(lessons: List<LessonApiModel>) {
        delete()
        _insert(lessons)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(lessons: List<LessonApiModel>)

    @Query("DELETE FROM lessonapimodel")
    suspend fun delete()

    @Query("SELECT * FROM lessonapimodel")
    suspend fun get(): List<LessonApiModel>

    @Query("SELECT * FROM lessonapimodel WHERE paragraphId = :uid")
    suspend fun get(uid: String): List<LessonApiModel>

    @Query("SELECT * FROM lessonapimodel WHERE id = :uid")
    suspend fun getById(uid: String): LessonApiModel

    @Query("SELECT * FROM lessonapimodel LIMIT 1")
    suspend fun getOneLesson(): List<LessonApiModel>
}
