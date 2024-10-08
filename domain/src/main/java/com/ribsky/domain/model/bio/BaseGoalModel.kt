package com.ribsky.domain.model.bio

interface BaseGoalModel {
    val id: Int
    val name: String

    data class Base(
        override val id: Int,
        override val name: String,
    ) : BaseGoalModel
}
