package com.ribsky.lesson.utils.checker.factory

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.model.ChatModel
import com.ribsky.lesson.utils.checker.strategy.ChipChecker
import com.ribsky.lesson.utils.checker.strategy.MistakeChecker
import com.ribsky.lesson.utils.checker.strategy.TestChecker
import com.ribsky.lesson.utils.checker.strategy.TranslateChecker
import com.ribsky.lesson.utils.checker.strategy.base.Checker

class CheckerFactoryImpl : CheckerFactory {
    @Suppress("UNCHECKED_CAST")
    override fun create(
        type: BaseContentModel.BaseContentType,
        userAnswer: Any,
        answer: Any?,
    ): Checker {
        return when (type) {
            is BaseContentModel.BaseContentType.FindMistakes -> MistakeChecker(
                userAnswer as String,
                answer as List<String>
            )

            is BaseContentModel.BaseContentType.TestPick -> TestChecker(userAnswer as ChatModel.Test.TestModel)
            is BaseContentModel.BaseContentType.TranslateChips -> ChipChecker(
                userAnswer as List<String>,
                answer as List<String>
            )

            is BaseContentModel.BaseContentType.TranslateText -> TranslateChecker(
                userAnswer as String,
                answer as List<String>
            )

            else -> throw IllegalArgumentException("Unknown type $type")
        }
    }
}
