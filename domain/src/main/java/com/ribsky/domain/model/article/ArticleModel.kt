package com.ribsky.domain.model.article

class ArticleModel(
    override val id: String,
    override val sort: Int,
    override val categories: List<String>,
    override val name: String,
    override val image: String,
    override val description: String,
    override val content: String,
    override val hasPrem: Boolean,
) : BaseArticleModel
