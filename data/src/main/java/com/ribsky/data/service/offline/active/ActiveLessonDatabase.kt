package com.ribsky.data.service.offline.active

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.ActiveApiModel

@Database(entities = [ActiveApiModel::class], version = 1, exportSchema = false)
abstract class ActiveLessonDatabase : RoomDatabase() {
    abstract val dao: ActiveLessonDao

    companion object {
        const val DATABASE_NAME = "active_lesson_database"
    }
}
