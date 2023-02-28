package com.ribsky.data.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val mapAdapter = moshi.adapter<Map<String, String>>(Map::class.java)

    @TypeConverter
    fun fromJson(string: String): Map<String, String> = mapAdapter.fromJson(string)!!

    @TypeConverter
    fun toJson(map: Map<String, String>): String = mapAdapter.toJson(map)
}
