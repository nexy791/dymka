package com.ribsky.data.utils.moshi

import com.ribsky.data.model.ContentApiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object ContentAdapter {

    private val _moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(ContentApiModel.ContentType::class.java, "type")
                .withSubtype(ContentApiModel.ContentType.Text::class.java, "text")
                .withSubtype(
                    ContentApiModel.ContentType.TranslateText::class.java,
                    "translate_text"
                )
                .withSubtype(
                    ContentApiModel.ContentType.TranslateChips::class.java,
                    "translate_chips"
                )
                .withSubtype(ContentApiModel.ContentType.TestPick::class.java, "test_pick")
                .withSubtype(ContentApiModel.ContentType.FindMistakes::class.java, "find_mistake")
                .withSubtype(ContentApiModel.ContentType.Image::class.java, "image")
        )
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val moshi get() = _moshi

    val content = adapter<ContentApiModel>()

    private inline fun <reified T> adapter(): JsonAdapter<T> = moshi.adapter(T::class.java)!!
}
