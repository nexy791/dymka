package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface ImageMapper : Mapper<BaseContentModel.BaseContentType.Image, ChatModel.Image>

class ImageMapperImpl : ImageMapper {
    override fun map(input: BaseContentModel.BaseContentType.Image): ChatModel.Image =
        ChatModel.Image(input.url)

}
