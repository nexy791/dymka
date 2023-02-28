package com.ribsky.domain.model.test

class TestModel(
    override val id: String,
    override val sort: Int,
    override val title: String,
    override val description: String,
    override val image: String,
    override val content: String,
    override val hasPrem: Boolean,
    override val colors: Map<String, String>,
) : BaseTestModel
