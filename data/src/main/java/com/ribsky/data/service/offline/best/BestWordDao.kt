package com.ribsky.data.service.offline.best

import androidx.room.*
import com.ribsky.data.model.BestWordApiModel

@Dao
interface BestWordDao {

    @Transaction
    suspend fun insert(word: List<BestWordApiModel>) {
        delete()
        _insert(word)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(word: List<BestWordApiModel>)

    @Query("DELETE FROM bestwordapimodel")
    suspend fun delete()

    @Query("SELECT * FROM bestwordapimodel")
    suspend fun get(): List<BestWordApiModel>

    @Query("SELECT * FROM bestwordapimodel WHERE id = :id")
    suspend fun get(id: Int): BestWordApiModel?

    @Query("SELECT * FROM bestwordapimodel LIMIT 1")
    suspend fun getOneWord(): List<BestWordApiModel>
}
