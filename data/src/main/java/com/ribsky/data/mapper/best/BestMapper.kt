package com.ribsky.data.mapper.best

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.BestWordApiModel
import com.ribsky.domain.model.best.BestWordModel

interface BestMapper : Mapper<BestWordApiModel, BestWordModel>

class BestMapperImpl : BestMapper {
    override fun map(input: BestWordApiModel): BestWordModel {
        return BestWordModel(
            title = input.title,
            description = input.description,
            id = input.id
        )
    }
}
