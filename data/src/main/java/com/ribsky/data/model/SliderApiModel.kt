package com.ribsky.data.model

import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SliderApiModel(
    override val content: List<SliderType?>
) : BaseSliderModel {

    sealed class SliderType : BaseSliderModel.BaseSliderType {

        @JsonClass(generateAdapter = true)
        class Text(
            override val text: String,
            override val type: String,
            override val background: String?
        ) : SliderType(), BaseSliderModel.BaseSliderType.Text

        @JsonClass(generateAdapter = true)
        class Image(
            override val image: String,
            override val text: String,
            override val type: String,
            override val background: String?,
        ) : SliderType(), BaseSliderModel.BaseSliderType.Image

        @JsonClass(generateAdapter = true)
        class Both(
            override val text: String,
            override val image: String,
            override val type: String,
            override val background: String?
        ) : SliderType(), BaseSliderModel.BaseSliderType.Both

    }

}