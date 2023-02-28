package com.ribsky.lesson.mapper.mappers

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.mapper.base.Mapper
import com.ribsky.lesson.model.ChatModel

interface TestPickMapper : Mapper<BaseContentModel.BaseContentType.TestPick, ChatModel.Test>

class TestPickMapperImpl : TestPickMapper {
    override fun map(input: BaseContentModel.BaseContentType.TestPick): ChatModel.Test {
        val test = input.text.first()
        return ChatModel.Test(
            test.originalText,
            test.translatedText.map { ChatModel.Test.TestModel(it.text, it.value) }
        )
    }
}
