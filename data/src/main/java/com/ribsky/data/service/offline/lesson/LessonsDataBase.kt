package com.ribsky.data.service.offline.lesson

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ribsky.data.converters.Converters
import com.ribsky.data.model.LessonApiModel

@TypeConverters(Converters::class)
@Database(entities = [LessonApiModel::class], version = 2, exportSchema = false)
abstract class LessonsDataBase : RoomDatabase() {
    abstract val dao: LessonsDao

    companion object {
        const val DATABASE_NAME = "lessons_database"
    }
}
