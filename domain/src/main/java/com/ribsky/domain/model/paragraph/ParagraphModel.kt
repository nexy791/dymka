package com.ribsky.domain.model.paragraph

data class ParagraphModel(
    override val id: String,
    override val sort: Int,
    override val description: String,
    override val image: String,
    override val name: String,
    override var percent: Float,
    override var isEmpty: Boolean,
    override var stars: Int = 0,
    override var starsHave: Int = 0,
    override var isEnoughStars: Boolean = false,
) : BaseParagraphModel
