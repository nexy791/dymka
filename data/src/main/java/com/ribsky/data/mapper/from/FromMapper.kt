package com.ribsky.data.mapper.from

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.FromApiModel
import com.ribsky.domain.model.bio.BaseFromModel

interface FromMapper : Mapper<FromApiModel, BaseFromModel>

class FromMapperImpl : FromMapper {
    override fun map(input: FromApiModel): BaseFromModel {
        return BaseFromModel.Base(
            id = input.id,
            name = input.name,
        )
    }
}
