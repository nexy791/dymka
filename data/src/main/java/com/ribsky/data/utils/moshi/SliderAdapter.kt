package com.ribsky.data.utils.moshi

import com.ribsky.data.model.SliderApiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object SliderAdapter {

    private val _moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(SliderApiModel.SliderType::class.java, "type")
                .withSubtype(SliderApiModel.SliderType.Text::class.java, "text")
                .withSubtype(SliderApiModel.SliderType.Image::class.java, "image")
                .withSubtype(SliderApiModel.SliderType.Both::class.java, "both")
        )
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val moshi get() = _moshi

    val content = adapter<SliderApiModel>()

    private inline fun <reified T> adapter(): JsonAdapter<T> = moshi.adapter(T::class.java)!!
}
