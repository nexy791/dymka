package com.ribsky.domain.model.article

interface BaseArticleModel {
    val id: String
    val sort: Int
    val categories: List<String>
    val name: String
    val image: String
    val description: String
    val content: String
    val hasPrem: Boolean

    fun isInProgress(): Boolean = content.isEmpty()
}
