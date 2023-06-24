package com.ribsky.data.mapper.level

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.LevelApiModel
import com.ribsky.domain.model.bio.BaseLevelModel

interface LevelMapper : Mapper<LevelApiModel, BaseLevelModel>

class LevelMapperImpl : LevelMapper {
    override fun map(input: LevelApiModel): BaseLevelModel {
        return BaseLevelModel.Base(
            id = input.id,
            name = input.name,
        )
    }
}
