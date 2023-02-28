package com.ribsky.data.repository.test

import com.ribsky.domain.model.test.TestModel

object AdditionTests {

    val additionTests = listOf(
        TestModel(
            id = "all",
            sort = 1000,
            title = "Всі тести",
            description = "Колекція всіх тестів",
            image = "gs://dymka-ua.appspot.com/images/books/ballot_black_24dp.svg",
            content = ".",
            hasPrem = true,
            colors = mapOf(
                "background" to "#50AE94",
                "primary" to "#EFEA4F"
            )
        ),
        TestModel(
            id = "fav",
            sort = 1001,
            title = "Обране",
            description = "Твої обрані слова",
            image = "gs://dymka-ua.appspot.com/images/books/favorite_border_black_24dp.svg",
            content = ".",
            hasPrem = true,
            colors = mapOf(
                "background" to "#ECF0F8",
                "primary" to "#F171A0"
            )
        )
    )

}