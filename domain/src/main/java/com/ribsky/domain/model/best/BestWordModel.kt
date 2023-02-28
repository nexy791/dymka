package com.ribsky.domain.model.best

data class BestWordModel(
    override val title: String,
    override val description: String,
    override val id: Int,
) : BaseBestWordModel
