package com.ribsky.lesson.mapper.factory

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.model.ChatModel

interface ChatMapperFactory {

    fun create(baseContentModel: BaseContentModel.BaseContentType): ChatModel

    fun createAnswer(text: String): ChatModel.Answer

    fun createTextFromUser(text: String): ChatModel.TextFromUser
}
