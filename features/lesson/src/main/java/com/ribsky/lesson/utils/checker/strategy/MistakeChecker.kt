package com.ribsky.lesson.utils.checker.strategy

import com.ribsky.lesson.utils.checker.strategy.base.Checker

class MistakeChecker(override val userAnswer: String, val answer: List<String>) : Checker {
    override fun checkAnswer(): Boolean {
        if (userAnswer.isEmpty()) return false
        return userAnswer in answer.map {
            it.replace(".", "").replace(",", "")
        }
    }
}
