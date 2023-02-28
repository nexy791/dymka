package com.ribsky.domain.model.lesson

interface BaseLessonModel {
    val id: String
    val sort: Int
    val paragraphId: String
    val name: String
    val description: String
    val content: String
    val hasPrem: Boolean
    var isDone: Boolean

    fun isInProgress(): Boolean = content.isEmpty()
}
