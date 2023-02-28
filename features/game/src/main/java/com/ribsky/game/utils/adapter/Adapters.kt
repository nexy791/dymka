package com.ribsky.game.utils.adapter

import com.ribsky.game.model.PayLoadModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Adapters {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val user = adapter<PayLoadModel.User>()

    val score = adapter<PayLoadModel.Score>()

    val config = adapter<PayLoadModel.Config>()

    val type = adapter<PayLoadModel.Type>()

    private inline fun <reified T> adapter(): JsonAdapter<T> = moshi.adapter(T::class.java)!!
}
