package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StarsApiModel(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val stars: Int = 0,
)
