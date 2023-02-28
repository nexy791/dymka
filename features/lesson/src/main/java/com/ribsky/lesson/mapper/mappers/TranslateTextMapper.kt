package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface TranslateTextMapper :
    Mapper<BaseContentModel.BaseContentType.TranslateText, ChatModel.TranslateText>

class TranslateTextMapperImpl : TranslateTextMapper {
    override fun map(input: BaseContentModel.BaseContentType.TranslateText): ChatModel.TranslateText =
        ChatModel.TranslateText(input.text.first().originalText)
}
