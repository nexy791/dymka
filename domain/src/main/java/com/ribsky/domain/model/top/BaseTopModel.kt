package com.ribsky.domain.model.top

interface BaseTopModel {
    val name: String
    val image: String
    val score: Int
    val streak: Int
    val lessonsCount: Int
    var hasPrem: Boolean
    var id: Int
    var bioLevel: Int
    var bioGoal: Int
}
