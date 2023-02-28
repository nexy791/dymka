package com.ribsky.lesson.utils.checker.strategy.base

interface Checker {

    val userAnswer: Any

    fun checkAnswer(): Boolean
}
