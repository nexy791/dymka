package com.ribsky.data.utils.moshi

import com.ribsky.data.model.BestWordApiModel
import com.ribsky.data.model.PromoApiModel
import com.ribsky.data.model.SliderApiModel
import com.ribsky.data.model.UserApiModel
import com.ribsky.data.model.WordApiModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Adapters {

    private val _moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val moshi get() = _moshi

    val user = adapter<UserApiModel>()

    val words = adapterList<WordApiModel>()

    val saved = adapterList<String>()

    val bestWords = adapterList<BestWordApiModel>()

    val promo = adapter<PromoApiModel>()

    private inline fun <reified T> adapter(): JsonAdapter<T> = moshi.adapter(T::class.java)!!

    private inline fun <reified T> adapterList(): JsonAdapter<List<T>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, T::class.java))!!
}
