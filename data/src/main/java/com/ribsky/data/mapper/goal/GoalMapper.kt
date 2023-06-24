package com.ribsky.data.mapper.goal

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.GoalApiModel
import com.ribsky.domain.model.bio.BaseGoalModel

interface GoalMapper : Mapper<GoalApiModel, BaseGoalModel>

class GoalMapperImpl : GoalMapper {
    override fun map(input: GoalApiModel): BaseGoalModel {
        return BaseGoalModel.Base(
            id = input.id,
            name = input.name,
        )
    }
}
