package com.ribsky.data.service.offline.stars

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.ActiveApiModel
import com.ribsky.data.model.StarsApiModel

@Database(entities = [StarsApiModel::class], version = 1, exportSchema = false)
abstract class StarsLessonDatabase : RoomDatabase() {
    abstract val dao: StarsLessonDao

    companion object {
        const val DATABASE_NAME = "stars_lesson_database"
    }
}
