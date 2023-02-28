package com.ribsky.data.model

import com.ribsky.domain.model.content.BaseContentModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentApiModel(
    override val content: List<ContentType?>,
) : BaseContentModel {

    sealed class ContentType : BaseContentModel.BaseContentType {

        @JsonClass(generateAdapter = true)
        class TranslatedModel(
            @Json(name = "original_text") override val originalText: String,
            @Json(name = "translated_text") override val translatedText: List<String>,
        ) : BaseContentModel.BaseContentType.TranslatedModel

        @JsonClass(generateAdapter = true)
        class MistakeModel(
            @Json(name = "original_text") override val originalText: String,
            @Json(name = "translated_text") override val translatedText: String,
        ) : BaseContentModel.BaseContentType.MistakeModel

        @JsonClass(generateAdapter = true)
        data class TestModel(
            @Json(name = "original_text") override val originalText: String,
            @Json(name = "translated_text") override val translatedText: List<TextModel>,
        ) : BaseContentModel.BaseContentType.TestModel {

            @JsonClass(generateAdapter = true)
            class TextModel(
                override val text: String,
                override val value: Boolean,
            ) : BaseContentModel.BaseContentType.TestModel.TextModel
        }

        @JsonClass(generateAdapter = true)
        data class ChipModel(
            @Json(name = "original_text") override val originalText: String,
            @Json(name = "translated_text") override val translatedText: List<String>,
            override val chips: List<String>,
        ) : BaseContentModel.BaseContentType.ChipModel

        @JsonClass(generateAdapter = true)
        class Text(
            override val text: String,
            override val type: String,
            override val action: String,
        ) : ContentType(),
            BaseContentModel.BaseContentType.Text

        @JsonClass(generateAdapter = true)
        class TranslateText(
            override val text: List<TranslatedModel>,
            override val type: String,
        ) : ContentType(), BaseContentModel.BaseContentType.TranslateText

        @JsonClass(generateAdapter = true)
        data class TranslateChips(
            override val text: List<ChipModel>,
            override val type: String,
        ) : ContentType(), BaseContentModel.BaseContentType.TranslateChips

        @JsonClass(generateAdapter = true)
        data class TestPick(
            override val text: List<TestModel>,
            override val type: String,
        ) : ContentType(), BaseContentModel.BaseContentType.TestPick

        @JsonClass(generateAdapter = true)
        class FindMistakes(
            override val text: List<MistakeModel>,
            override val type: String,
        ) : ContentType(), BaseContentModel.BaseContentType.FindMistakes

        @JsonClass(generateAdapter = true)
        class Image(
            override val url: String,
            override val type: String,
        ) : ContentType(), BaseContentModel.BaseContentType.Image
    }

    companion object {

        fun ContentApiModel.shuffle(): ContentApiModel = runCatching {
            val list = content.map {
                when (it) {
                    is ContentType.Text -> it
                    is ContentType.Image -> it
                    is ContentType.TranslateText -> it
                    is ContentType.FindMistakes -> it
                    is ContentType.TestPick -> it.copy(
                        text = it.text.map { t ->
                            t.copy(
                                originalText = t.originalText,
                                translatedText = t.translatedText.shuffled()
                            )
                        }
                    )
                    is ContentType.TranslateChips -> it.copy(
                        text = it.text.map { t ->
                            t.copy(
                                originalText = t.originalText,
                                chips = t.chips.shuffled()
                            )
                        }
                    )
                    else -> null
                }
            }
            copy(content = list)
        }.getOrNull()!!
    }
}
