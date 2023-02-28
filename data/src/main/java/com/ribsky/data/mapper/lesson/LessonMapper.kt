package com.ribsky.data.mapper.lesson

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.LessonApiModel
import com.ribsky.domain.model.lesson.LessonModel

interface LessonMapper : Mapper<LessonApiModel, LessonModel>

class LessonMapperImpl : LessonMapper {
    override fun map(input: LessonApiModel): LessonModel {
        return LessonModel(
            id = input.id,
            sort = input.sort,
            paragraphId = input.paragraphId,
            name = input.name,
            description = input.description,
            content = input.content,
            hasPrem = input.hasPrem,
            isDone = false,
        )
    }
}
