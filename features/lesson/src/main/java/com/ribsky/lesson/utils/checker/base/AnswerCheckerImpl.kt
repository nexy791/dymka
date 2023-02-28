package com.ribsky.lesson.utils.checker.base

import com.ribsky.lesson.utils.checker.strategy.base.Checker

class AnswerCheckerImpl(
    private val checker: Checker,
) : AnswerChecker {
    override fun checkAnswer(): Boolean = checker.checkAnswer()
}
