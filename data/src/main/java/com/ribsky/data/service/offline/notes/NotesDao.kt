package com.ribsky.data.service.offline.notes

import androidx.room.*
import com.ribsky.data.model.NoteApiModel

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteApiModel)

    @Query("DELETE FROM noteapimodel WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM noteapimodel WHERE paragraphId = :paragraphId")
    suspend fun get(paragraphId: String): List<NoteApiModel>

}
