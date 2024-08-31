package com.ribsky.test.utils

class EmojiMapper {

    private val emojis = mapOf(
        0 to "\uD83D\uDE10",
        5 to "🤔",
        10 to "😳",
        15 to "😱",
        20 to "\uD83E\uDEE3",
        25 to "\uD83E\uDD49",
        30 to "\uD83E\uDD48",
        35 to "\uD83E\uDD47",
        40 to "\uD83C\uDFC6",
        45 to "\uD83D\uDD25",
        50 to "\uD83C\uDFAF",
        55 to "\uD83C\uDF1F",
        60 to "\uD83C\uDF93",
        65 to "\uD83C\uDF89",
        70 to "\uD83D\uDC23",
        75 to "\uD83D\uDC25",
        80 to "\uD83D\uDC31",
        85 to "\uD83D\uDE40",
        90 to "\uD83D\uDE3B",
        95 to "\uD83D\uDE3D",
        100 to "❤\uFE0F",
        105 to "❤\uFE0F\u200D\uD83D\uDD25"
    )

    fun getEmoji(score: Int): String? = emojis.keys.lastOrNull { it <= score }?.let { emojis[it] }

}