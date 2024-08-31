package com.ribsky.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PromoApiModel(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "background") val background: String,
    @Json(name = "color") val color: String,
    @Json(name = "link") val link: String,
)