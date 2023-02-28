package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestApiModel(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val sort: Int = 0,
    val title: String = "",
    val image: String = "",
    val content: String = "",
    val description: String = "",
    val hasPrem: Boolean = false,
    val colors: Map<String, String> = mapOf(
        "background" to "#FFFFFF",
        "primary" to "#000000"
    ),
)
