package com.ribsky.lesson.mapper.base

interface Mapper<I, O> {
    fun map(input: I): O
}
