package com.ribsky.dymka.di

import com.ribsky.data.mapper.best.BestMapper
import com.ribsky.data.mapper.best.BestMapperImpl
import com.ribsky.data.mapper.lesson.LessonMapper
import com.ribsky.data.mapper.lesson.LessonMapperImpl
import com.ribsky.data.mapper.paragraph.ParagraphMapper
import com.ribsky.data.mapper.paragraph.ParagraphMapperImpl
import com.ribsky.data.mapper.test.TestMapper
import com.ribsky.data.mapper.test.TestMapperImpl
import com.ribsky.data.mapper.top.TopMapper
import com.ribsky.data.mapper.top.TopMapperImpl
import com.ribsky.lesson.mapper.factory.ChatMapperFactory
import com.ribsky.lesson.mapper.factory.ChatMapperFactoryImpl
import com.ribsky.lesson.mapper.mappers.*
import org.koin.dsl.module

val mapperDi = module {

    factory<ParagraphMapper> {
        ParagraphMapperImpl()
    }

    factory<LessonMapper> {
        LessonMapperImpl()
    }

    factory<TestMapper> {
        TestMapperImpl()
    }

    factory<TopMapper> {
        TopMapperImpl()
    }

    factory<BestMapper> {
        BestMapperImpl()
    }

    factory<ChatMapperFactory> {
        ChatMapperFactoryImpl(
            textMapper = get(),
            translateTextMapper = get(),
            findMistakesMapper = get(),
            imageMapper = get(),
            testPickMapper = get(),
            translateChipsMapper = get()
        )
    }

    factory<TextMapper> {
        TextMapperImpl()
    }

    factory<TranslateTextMapper> {
        TranslateTextMapperImpl()
    }

    factory<FindMistakesMapper> {
        FindMistakesMapperImpl()
    }

    factory<ImageMapper> {
        ImageMapperImpl()
    }

    factory<TestPickMapper> {
        TestPickMapperImpl()
    }

    factory<TranslateChipsMapper> {
        TranslateChipsMapperImpl()
    }
}
