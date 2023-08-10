package com.ribsky.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlin.random.Random

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

    var starsCount: Int = 0,
) {
    enum class Type {
        TEST, LESSON, STREAK, PREMIUM, STAR,
        NEAR_STARS, NEAR_TEST
    }

    companion object {

        fun fromUserApi(
            userApiModel: UserApiModel,
            type: Type,
        ) = TopApiModel(
            name = userApiModel.name,
            image = userApiModel.image,
            score = userApiModel.score,
            streak = userApiModel.streak,
            lessons = userApiModel.lessons,
            lessonsCount = userApiModel.lessonsCount,
            hasPrem = userApiModel.hasPrem,
            id = -Random.nextInt(0, 1000000),
            type = type,
            bioLevel = userApiModel.bioLevel,
            bioGoal = userApiModel.bioGoal,
            starsCount = userApiModel.starsCount,
        )

    }
}
