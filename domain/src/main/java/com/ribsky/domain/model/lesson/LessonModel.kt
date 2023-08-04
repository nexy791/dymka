package com.ribsky.domain.model.lesson

class LessonModel(
    override val id: String,
    override val sort: Int,
    override val paragraphId: String,
    override val name: String,
    override val description: String,
    override val content: String,
    override val hasPrem: Boolean,
    override var isDone: Boolean,
    override var stars: Int,
) : BaseLessonModel
