package com.ribsky.data.service.offline.active

import androidx.room.*
import com.ribsky.data.model.ActiveApiModel

@Dao
interface ActiveLessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(active: ActiveApiModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insert(active: List<ActiveApiModel>)

    @Transaction
    suspend fun insert(active: List<ActiveApiModel>) {
        delete()
        _insert(active)
    }

    @Query("DELETE FROM activeapimodel")
    suspend fun delete()

    @Query("SELECT * FROM activeapimodel")
    suspend fun get(): List<ActiveApiModel>
}
