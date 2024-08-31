package com.ribsky.data.service.offline.article

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ribsky.data.converters.Converters
import com.ribsky.data.model.ArticleApiModel

@TypeConverters(Converters::class)
@Database(entities = [ArticleApiModel::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract val dao: ArticlesDao

    companion object {
        const val DATABASE_NAME = "articles_database"
    }
}
