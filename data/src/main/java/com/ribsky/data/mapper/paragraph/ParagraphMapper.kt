package com.ribsky.data.mapper.paragraph

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.ParagraphApiModel
import com.ribsky.domain.model.paragraph.ParagraphModel

interface ParagraphMapper : Mapper<ParagraphApiModel, ParagraphModel>

class ParagraphMapperImpl : ParagraphMapper {
    override fun map(input: ParagraphApiModel): ParagraphModel {
        return ParagraphModel(
            id = input.id,
            sort = input.sort,
            description = input.description,
            image = input.image,
            name = input.name,
            percent = 0f,
            isEmpty = false,
        )
    }
}
