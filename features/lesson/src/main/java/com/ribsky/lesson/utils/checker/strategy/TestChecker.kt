package com.ribsky.lesson.utils.checker.strategy

import com.ribsky.lesson.model.ChatModel
import com.ribsky.lesson.utils.checker.strategy.base.Checker

class TestChecker(override val userAnswer: ChatModel.Test.TestModel) :
    Checker {
    override fun checkAnswer(): Boolean = userAnswer.isTrue
}
