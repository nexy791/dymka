package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface TranslateChipsMapper :
    Mapper<BaseContentModel.BaseContentType.TranslateChips, ChatModel.Chips>

class TranslateChipsMapperImpl : TranslateChipsMapper {
    override fun map(input: BaseContentModel.BaseContentType.TranslateChips): ChatModel.Chips {
        val chip = input.text.first()
        return ChatModel.Chips(chip.originalText, chip.chips)
    }
}
