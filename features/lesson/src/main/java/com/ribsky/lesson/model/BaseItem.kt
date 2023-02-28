package com.ribsky.lesson.model

import com.ribsky.lesson.adapter.chat.factory.type.TypeFactory

interface BaseItem {

    val id: Int
    fun type(typeFactory: TypeFactory): Int
}
