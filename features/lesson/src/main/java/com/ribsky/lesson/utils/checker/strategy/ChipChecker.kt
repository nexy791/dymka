package com.ribsky.lesson.utils.checker.strategy

import com.ribsky.lesson.utils.checker.strategy.base.Checker

class ChipChecker(override val userAnswer: List<String>, val answer: List<String>) :
    Checker {
    override fun checkAnswer(): Boolean {
        if (userAnswer.isEmpty()) return false
        return answer.containsAll(userAnswer) && answer.size == userAnswer.size
    }
}
