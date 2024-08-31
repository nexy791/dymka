package com.ribsky.articles.model

data class CategoryModel(
    val name: String,
    val type: Type,
    val selected: Boolean = false
) {
    enum class Type {
        ALL,
        SAVED,
        OTHER
    }

    companion object {
        fun default() = listOf(
            CategoryModel(
                name = "\uD83D\uDC08 Всі",
                type = Type.ALL,
                selected = true
            ),
//            CategoryModel(
//                name = "⭐\uFE0F Збережені",
//                type = Type.SAVED,
//                selected = false
//            )
        )
    }

}