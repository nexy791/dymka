package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class TopApiModel(
    var name: String = "",
    var image: String = "",
    var score: Int = 0,
    var streak: Int = 0,
    @Ignore var lessons: Map<String, String> = HashMap(),
    var lessonsCount: Int = 0,
    var hasPrem: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var type: Type? = null,

    var bioLevel: Int = -1,
    var bioGoal: Int = -1,
) {

    enum class Type {
        TEST, LESSON, STREAK, PREMIUM
    }
}
