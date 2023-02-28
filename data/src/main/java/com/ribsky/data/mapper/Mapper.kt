package com.ribsky.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
