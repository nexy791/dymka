package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleApiModel(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val sort: Int = 0,
    val name: String = "",
    val description: String = "",
    val content: String = "",
    val categories: List<String> = emptyList(),
    val image: String = "",
    val hasPrem: Boolean = false,
)
