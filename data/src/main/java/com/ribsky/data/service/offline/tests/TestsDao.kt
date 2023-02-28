package com.ribsky.data.service.offline.tests

import androidx.room.*
import com.ribsky.data.model.TestApiModel

@Dao
interface TestsDao {

    @Transaction
    suspend fun insert(books: List<TestApiModel>) {
        delete()
        _insert(books)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(books: List<TestApiModel>)

    @Query("DELETE FROM testapimodel")
    suspend fun delete()

    @Query("SELECT * FROM testapimodel")
    suspend fun get(): List<TestApiModel>

    @Query("SELECT * FROM testapimodel LIMIT 1")
    suspend fun getOneBook(): List<TestApiModel>

    @Query("SELECT * FROM testapimodel WHERE id = :id")
    suspend fun getBookById(id: String): TestApiModel
}
