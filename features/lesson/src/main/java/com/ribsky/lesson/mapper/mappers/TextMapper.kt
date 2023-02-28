package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface TextMapper : Mapper<BaseContentModel.BaseContentType.Text, ChatModel.Text>

class TextMapperImpl : TextMapper {
    override fun map(input: BaseContentModel.BaseContentType.Text): ChatModel.Text =
        ChatModel.Text(input.text)
}
