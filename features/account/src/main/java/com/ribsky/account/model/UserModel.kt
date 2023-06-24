package com.ribsky.account.model

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.model.user.BaseUserModel

data class UserModel(
    override val name: String,
    override val email: String = "",
    override val image: String,
    override val score: Int,
    override val lessons: Map<String, String> = mapOf(),
    override val lessonsCount: Int,
    override val saved: Map<String, String> = mapOf(),
    override var version: Long = 0,
    override var hasPrem: Boolean,
    override var hasDiscount: Boolean,
    override var botTotalCount: Long,
    override var streak: Int,
    override var streakLastDay: Long,
    override var bioLevel: Int,
    override var bioGoal: Int,
) : BaseUserModel {

    constructor(user: BaseTopModel) : this(
        name = user.name,
        image = user.image,
        score = user.score,
        lessonsCount = user.lessonsCount,
        hasPrem = user.hasPrem,
        hasDiscount = false,
        botTotalCount = 0,
        streak = user.streak,
        streakLastDay = 0,
        bioLevel = user.bioLevel,
        bioGoal = user.bioGoal,
    )
}
