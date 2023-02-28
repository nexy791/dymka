package com.ribsky.data.service.offline.top

import androidx.room.*
import com.ribsky.data.model.TopApiModel

@Dao
interface TopDao {

    @Transaction
    suspend fun insert(active: List<TopApiModel>) {
        delete()
        _insert(active)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(active: List<TopApiModel>)

    @Query("SELECT * FROM topapimodel WHERE id = :id")
    suspend fun get(id: Int): TopApiModel

    @Query("DELETE FROM topapimodel")
    suspend fun delete()

    @Query("SELECT * FROM topapimodel")
    suspend fun get(): List<TopApiModel>

    @Query("SELECT * FROM topapimodel LIMIT 1")
    suspend fun getFirst(): TopApiModel?
}
