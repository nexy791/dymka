package com.ribsky.top.model

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.model.user.BaseUserModel

data class TopModel(
    val topModel: BaseTopModel,
    var position: Int,
    var type: ViewType,
) : BaseTopModel by topModel {

    enum class ViewType(val type: Int) {
        LESSON(0),
        TEST(1)
    }

    constructor(baseUserModel: BaseUserModel, position: Int, type: ViewType) : this(
        com.ribsky.domain.model.top.TopModel(
            baseUserModel.name,
            baseUserModel.image,
            baseUserModel.score,
            baseUserModel.lessonsCount,
            baseUserModel.hasPrem,
            0
        ),
        position,
        type
    )
}
