package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ParagraphApiModel(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val sort: Int = 0,
    val description: String = "",
    val image: String = "",
    val name: String = "",
    val stars: Int = 0,
)
