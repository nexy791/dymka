package com.ribsky.data.service.offline.stars

import androidx.room.*
import com.ribsky.data.model.StarsApiModel

@Dao
interface StarsLessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(active: StarsApiModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(active: List<StarsApiModel>)

    @Transaction
    suspend fun insert(active: List<StarsApiModel>) {
        delete()
        _insert(active)
    }

    @Query("DELETE FROM starsapimodel")
    suspend fun delete()

    @Query("SELECT * FROM starsapimodel")
    suspend fun get(): List<StarsApiModel>

    @Query("SELECT * FROM starsapimodel WHERE id = :id")
    suspend fun get(id: String): StarsApiModel?
}
