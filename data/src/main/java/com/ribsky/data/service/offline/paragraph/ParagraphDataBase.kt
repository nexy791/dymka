package com.ribsky.data.service.offline.paragraph

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.ParagraphApiModel

@Database(entities = [ParagraphApiModel::class], version = 1, exportSchema = false)
abstract class ParagraphDataBase : RoomDatabase() {
    abstract val dao: ParagraphDao

    companion object {
        const val DATABASE_NAME = "paragraph.db"
    }
}
