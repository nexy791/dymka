package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteApiModel(
    val note: String,
    val paragraphId: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
