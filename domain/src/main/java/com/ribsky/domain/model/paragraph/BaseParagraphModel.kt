package com.ribsky.domain.model.paragraph

interface BaseParagraphModel {

    val id: String
    val sort: Int
    val description: String
    val image: String
    val name: String
    var percent: Float
    var isEmpty: Boolean
}
