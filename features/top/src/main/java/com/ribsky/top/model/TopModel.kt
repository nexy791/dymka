package com.ribsky.top.model

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.model.user.BaseUserModel

data class TopModel(
    val topModel: BaseTopModel,
    var position: Int,
    var type: ViewType,
) : BaseTopModel by topModel {

    enum class ViewType(val type: Int) {
        TEST(0),
        STREAK(1),
        STARS(2)
    }

    constructor(baseUserModel: BaseUserModel, position: Int, type: ViewType) : this(
        com.ribsky.domain.model.top.TopModel(
            name = baseUserModel.name,
            image = baseUserModel.image,
            score = baseUserModel.score,
            lessonsCount = baseUserModel.lessonsCount,
            hasPrem = baseUserModel.hasPrem,
            id = 0,
            streak = baseUserModel.streak,
            bioLevel = baseUserModel.bioLevel,
            bioGoal = baseUserModel.bioGoal,
            starsCount = baseUserModel.starsCount,
        ),
        position,
        type
    )
}
