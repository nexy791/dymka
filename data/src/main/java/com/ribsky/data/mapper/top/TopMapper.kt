package com.ribsky.data.mapper.top

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.TopApiModel
import com.ribsky.domain.model.top.TopModel

interface TopMapper : Mapper<TopApiModel, TopModel>

class TopMapperImpl : TopMapper {
    override fun map(input: TopApiModel): TopModel {
        return TopModel(
            name = input.name,
            image = input.image,
            score = input.score,
            lessonsCount = input.lessonsCount,
            hasPrem = input.hasPrem,
            id = input.id,
            streak = input.streak,
            bioLevel = input.bioLevel,
            bioGoal = input.bioGoal,
        )
    }
}
