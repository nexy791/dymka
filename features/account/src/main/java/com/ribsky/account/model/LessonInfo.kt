package com.ribsky.account.model

data class LessonInfo(val count: Int, val lessons: Int) {
    fun calculatePercent() = (count * 100) / lessons
}
