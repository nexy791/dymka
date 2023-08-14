package com.ribsky.data.service.offline.notes

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.NoteApiModel

@Database(entities = [NoteApiModel::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {
    abstract val dao: NotesDao

    companion object {
        const val DATABASE_NAME = "notes.db"
    }
}
