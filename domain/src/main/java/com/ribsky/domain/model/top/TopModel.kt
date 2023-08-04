package com.ribsky.domain.model.top

data class TopModel(
    override val name: String,
    override val image: String,
    override val score: Int,
    override val lessonsCount: Int,
    override var hasPrem: Boolean,
    override var id: Int,
    override val streak: Int,
    override var bioLevel: Int,
    override var bioGoal: Int,
    override var starsCount: Int,
) : BaseTopModel
