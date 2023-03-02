package com.ribsky.data.service.offline.best

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.BestWordApiModel

@Database(entities = [BestWordApiModel::class], version = 3, exportSchema = false)
abstract class BestWordDatabase : RoomDatabase() {
    abstract val dao: BestWordDao

    companion object {
        const val DATABASE_NAME = "best_word_database"
    }
}
