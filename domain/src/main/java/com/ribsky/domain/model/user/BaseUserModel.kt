package com.ribsky.domain.model.user

interface BaseUserModel {
    val name: String
    val email: String
    val image: String
    val score: Int
    val lessons: Map<String, String>
    val lessonsCount: Int
    val saved: Map<String, String>

    var version: Long
    var hasPrem: Boolean

    var hasDiscount: Boolean

    var botTotalCount: Long

    var streak: Int
    var streakLastDay: Long

    var bioLevel: Int
    var bioGoal: Int
    var bioFrom: Int

    var stars: Map<String, Int>
    var starsCount: Int
}
