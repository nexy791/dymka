package com.ribsky.lesson.utils.checker.factory

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.lesson.utils.checker.strategy.base.Checker

interface CheckerFactory {
    fun create(type: BaseContentModel.BaseContentType, userAnswer: Any, answer: Any?): Checker
}
