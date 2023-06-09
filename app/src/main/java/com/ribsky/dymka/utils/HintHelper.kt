package com.ribsky.dymka.utils

object HintHelper {

    private val hints = listOf(
        "Вірю в тебе!",
        "Мова — це сила!",
        "Все буде добре! 💓",
        "Підтримуй інших!",
        "У тебе все вийде!",
        "Ти все можеш!",
        "Саме час вчитися! 📚",
        "Не зупиняйся!",
        "Не забувай відпочивати!",
        "Як ти сьогодні?",
        "Твої знання фантастичні!",
        "Я тобою пишаюсь!",
        "Вір у себе!",
        "Не забувай про сон!",
        "Гарного настрою!",
        "Гарного дня!",
        "Бажаю успіхів!",
        "Сил тобі!",
        "Знаю, що ти можеш!",
        "Це твій день! (читай це щодня 😉)",
        "Ціль — це шлях!",
        "Спілкуйся українською! 💙💛",
        "Як щодо тестів?",
        "Як щодо вправ?",
        "Няв! 🐱",
        "Бережи свої сили!",
        "Бережи себе!",
        "Сподіваюсь ти в безпеці!",
        "Я сумував без тебе!",
        "Будь котиком!",
        "Нумо вчитися!",
        "Вперед до нових знань!"
    )

    val randomHint: String
        get() = hints.random()
}