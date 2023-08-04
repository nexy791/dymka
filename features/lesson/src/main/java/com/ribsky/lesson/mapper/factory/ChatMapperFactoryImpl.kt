package com.ribsky.lesson.mapper.factory

import com.ribsky.domain.model.content.BaseContentModel.BaseContentType
import com.ribsky.lesson.mapper.mappers.FindMistakesMapper
import com.ribsky.lesson.mapper.mappers.ImageMapper
import com.ribsky.lesson.mapper.mappers.TestPickMapper
import com.ribsky.lesson.mapper.mappers.TextMapper
import com.ribsky.lesson.mapper.mappers.TranslateChipsMapper
import com.ribsky.lesson.mapper.mappers.TranslateTextMapper
import com.ribsky.lesson.model.ChatModel

class ChatMapperFactoryImpl(
    private val textMapper: TextMapper,
    private val translateTextMapper: TranslateTextMapper,
    private val findMistakesMapper: FindMistakesMapper,
    private val imageMapper: ImageMapper,
    private val testPickMapper: TestPickMapper,
    private val translateChipsMapper: TranslateChipsMapper,
) : ChatMapperFactory {
    override fun create(baseContentModel: BaseContentType): ChatModel =
        when (baseContentModel) {
            is BaseContentType.Text -> textMapper.map(baseContentModel)
            is BaseContentType.TranslateText -> translateTextMapper.map(baseContentModel)
            is BaseContentType.FindMistakes -> findMistakesMapper.map(baseContentModel)
            is BaseContentType.Image -> imageMapper.map(baseContentModel)
            is BaseContentType.TestPick -> testPickMapper.map(baseContentModel)
            is BaseContentType.TranslateChips -> translateChipsMapper.map(baseContentModel)
            else -> throw IllegalArgumentException("Unknown type of BaseContentType")
        }

    override fun createAnswer(text: String, isCorrect: Boolean): ChatModel.Answer =
        ChatModel.Answer(text, isCorrect = isCorrect)

    override fun createTextFromUser(text: String): ChatModel.TextFromUser =
        ChatModel.TextFromUser(text)
}
