package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface FindMistakesMapper :
    Mapper<BaseContentModel.BaseContentType.FindMistakes, ChatModel.Mistake>

class FindMistakesMapperImpl : FindMistakesMapper {
    override fun map(input: BaseContentModel.BaseContentType.FindMistakes): ChatModel.Mistake =
        ChatModel.Mistake(input.text.first().originalText)
}
