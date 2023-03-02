package com.ribsky.data.service.offline.top

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.TopApiModel

@Database(entities = [TopApiModel::class], version = 3, exportSchema = false)
abstract class TopDatabase : RoomDatabase() {
    abstract val dao: TopDao

    companion object {
        const val DATABASE_NAME = "top_database"
    }
}
