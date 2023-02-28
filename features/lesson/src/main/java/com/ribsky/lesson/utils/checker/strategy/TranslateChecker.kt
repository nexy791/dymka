package com.ribsky.lesson.utils.checker.strategy

import com.ribsky.lesson.utils.checker.strategy.base.Checker

class TranslateChecker(override val userAnswer: String, val answer: List<String>) :
    Checker {
    override fun checkAnswer(): Boolean {
        if (userAnswer.isBlank()) return false
        return userAnswer.trim().lowercase() in answer.map { it.lowercase() }
    }
}
