package com.ribsky.domain.model.content

interface BaseContentModel {
    val content: List<BaseContentType?>

    interface BaseContentType {

        val type: String
        val answers: List<String>?

        interface TranslatedModel {
            val originalText: String
            val translatedText: List<String>
        }

        interface MistakeModel {
            val originalText: String
            val translatedText: String
        }

        interface TestModel {

            interface TextModel {
                val text: String
                val value: Boolean
            }

            val originalText: String
            val translatedText: List<TextModel>
        }

        interface ChipModel {
            val originalText: String
            val translatedText: List<String>
            val chips: List<String>
        }

        interface Text : BaseContentType {
            val text: String
            val action: String
            override val answers get() = null
        }

        interface TranslateText : BaseContentType {
            val text: List<TranslatedModel>
            override val answers get() = text.first().translatedText
        }

        interface TranslateChips : BaseContentType {
            val text: List<ChipModel>
            override val answers get() = text.first().translatedText
        }

        interface TestPick : BaseContentType {
            val text: List<TestModel>
            override val answers get() = null
        }

        interface FindMistakes : BaseContentType {
            val text: List<MistakeModel>
            override val answers
                get() = text.first().originalText.split(" ")
                    .filter { it !in text.first().translatedText.split(" ") }
        }

        interface Image : BaseContentType {
            val url: String
            override val answers get() = null
        }
    }
}
