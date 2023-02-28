package com.ribsky.data.service.offline.paragraph

import androidx.room.*
import com.ribsky.data.model.ParagraphApiModel

@Dao
interface ParagraphDao {

    @Transaction
    suspend fun insert(lessons: List<ParagraphApiModel>) {
        delete()
        _insert(lessons)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(lessons: List<ParagraphApiModel>)

    @Query("DELETE FROM paragraphapimodel")
    suspend fun delete()

    @Query("SELECT * FROM paragraphapimodel")
    suspend fun get(): List<ParagraphApiModel>

    @Query("SELECT * FROM paragraphapimodel WHERE id = :id")
    suspend fun get(id: String): ParagraphApiModel

    @Query("SELECT * FROM paragraphapimodel LIMIT 1")
    suspend fun getOneParagraph(): List<ParagraphApiModel>
}
