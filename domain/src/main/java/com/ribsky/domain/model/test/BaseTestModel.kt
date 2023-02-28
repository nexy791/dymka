package com.ribsky.domain.model.test

interface BaseTestModel {

    val id: String
    val sort: Int
    val title: String
    val description: String
    val image: String
    val content: String
    val hasPrem: Boolean
    val colors: Map<String, String>

    fun getPrimaryColor(): String = colors.getOrElse("primary") { "#000000" }
    fun getBackgroundColor(): String = colors.getOrElse("background") { "#000000" }

    fun isInProgress(): Boolean = content.isEmpty()
}
