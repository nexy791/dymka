package com.ribsky.shop.model

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.model.top.TopModel
import com.ribsky.domain.model.user.BaseUserModel

data class CatModel(
    val topModel: BaseTopModel,
) : BaseTopModel by topModel {

    constructor(topModel: BaseUserModel) : this(
        TopModel(
            name = topModel.name,
            image = topModel.image,
            score = topModel.score,
            lessonsCount = topModel.lessonsCount,
            hasPrem = topModel.hasPrem,
            id = 0,
            streak = topModel.streak,
            bioLevel = topModel.bioLevel,
            bioGoal = topModel.bioGoal,
        )
    )

}
