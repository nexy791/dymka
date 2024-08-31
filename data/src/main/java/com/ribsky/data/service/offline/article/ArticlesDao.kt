package com.ribsky.data.service.offline.article

import androidx.room.*
import com.ribsky.data.model.ArticleApiModel

@Dao
interface ArticlesDao {

    @Transaction
    suspend fun insert(lessons: List<ArticleApiModel>) {
        delete()
        _insert(lessons)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(articles: List<ArticleApiModel>)

    @Query("DELETE FROM articleapimodel")
    suspend fun delete()

    @Query("SELECT * FROM articleapimodel")
    suspend fun get(): List<ArticleApiModel>

    @Query("SELECT * FROM articleapimodel WHERE categories IN (:categoryIds)")
    suspend fun get(categoryIds: List<Int>): List<ArticleApiModel>

    @Query("SELECT * FROM articleapimodel WHERE id = :uid")
    suspend fun getById(uid: String): ArticleApiModel

    @Query("SELECT * FROM articleapimodel LIMIT 1")
    suspend fun getOneArticle(): List<ArticleApiModel>
}
