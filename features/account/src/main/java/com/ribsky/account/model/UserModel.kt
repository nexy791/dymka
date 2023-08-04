package com.ribsky.account.model

data class UserModel(
    val name: String,
    val email: String,
    val image: String,
    val isPrem: Boolean,
    val streak: StreakModel,
    val lessons: LessonModel,
    val books: Int,
    val stars: StarsModel,
    val bio: List<String>,
) {

    data class StreakModel(val day: Int, val isStreak: Boolean)
    data class LessonModel(val count: Int, val lessons: Int) {
        fun calculatePercent() = (count * 100) / lessons
    }

    data class StarsModel(val count: Int, val stars: Int)

}
