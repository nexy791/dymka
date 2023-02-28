package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActiveApiModel(
    @PrimaryKey(autoGenerate = false) val id: String = "",
)
