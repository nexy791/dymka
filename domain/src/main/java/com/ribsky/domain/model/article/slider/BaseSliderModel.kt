package com.ribsky.domain.model.article.slider

interface BaseSliderModel {

    val content: List<BaseSliderType?>

    interface BaseSliderType {

        val type: String
        val background: String?
        val text: String?


        interface Text : BaseSliderType {
            override val text: String?
        }

        interface Image : BaseSliderType {
            override val text: String?
            val image: String
        }

        interface Both : Text, Image

    }

}