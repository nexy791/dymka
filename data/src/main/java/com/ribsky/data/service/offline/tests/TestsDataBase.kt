package com.ribsky.data.service.offline.tests

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ribsky.data.converters.Converters
import com.ribsky.data.model.TestApiModel

@TypeConverters(Converters::class)
@Database(entities = [TestApiModel::class], version = 3, exportSchema = false)
abstract class TestsDataBase : RoomDatabase() {
    abstract val dao: TestsDao

    companion object {
        const val DATABASE_NAME = "books_database"
    }
}
