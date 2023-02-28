package com.ribsky.core.di

import android.content.Context
import androidx.room.Room
import com.ribsky.data.service.offline.active.ActiveLessonDao
import com.ribsky.data.service.offline.active.ActiveLessonDatabase
import com.ribsky.data.service.offline.best.BestWordDao
import com.ribsky.data.service.offline.best.BestWordDatabase
import com.ribsky.data.service.offline.lesson.LessonsDao
import com.ribsky.data.service.offline.lesson.LessonsDataBase
import com.ribsky.data.service.offline.paragraph.ParagraphDao
import com.ribsky.data.service.offline.paragraph.ParagraphDataBase
import com.ribsky.data.service.offline.tests.TestsDao
import com.ribsky.data.service.offline.tests.TestsDataBase
import com.ribsky.data.service.offline.top.TopDatabase
import org.koin.dsl.module

val dbDi = module {

    single { activeBestWordDb(get()) }

    single { lessonsDb(get()) }

    single { booksDb(get()) }

    single { paragraphDb(get()) }

    single { activeDb(get()) }

    single { topDb(get()) }

    single { bestWordDao(get()) }

    single { lessonsDao(get()) }

    single { booksDao(get()) }

    single { paragraphDao(get()) }

    single { activeDao(get()) }

    single { topDao(get()) }
}

fun bestWordDao(bestWordDatabase: BestWordDatabase): BestWordDao = bestWordDatabase.dao

fun lessonsDao(lessonsDatabase: LessonsDataBase): LessonsDao = lessonsDatabase.dao

fun booksDao(testsDatabase: TestsDataBase): TestsDao = testsDatabase.dao

fun paragraphDao(paragraphDatabase: ParagraphDataBase): ParagraphDao = paragraphDatabase.dao

fun activeDao(activeLessonDatabase: ActiveLessonDatabase): ActiveLessonDao =
    activeLessonDatabase.dao

fun topDao(topDatabase: TopDatabase) = topDatabase.dao

fun activeBestWordDb(context: Context): BestWordDatabase {
    return Room.databaseBuilder(
        context,
        BestWordDatabase::class.java,
        BestWordDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun booksDb(context: Context): TestsDataBase {
    return Room.databaseBuilder(context, TestsDataBase::class.java, TestsDataBase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun lessonsDb(context: Context): LessonsDataBase {
    return Room.databaseBuilder(context, LessonsDataBase::class.java, LessonsDataBase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun paragraphDb(context: Context): ParagraphDataBase {
    return Room.databaseBuilder(
        context,
        ParagraphDataBase::class.java,
        ParagraphDataBase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun activeDb(context: Context): ActiveLessonDatabase {
    return Room.databaseBuilder(
        context,
        ActiveLessonDatabase::class.java,
        ActiveLessonDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun topDb(context: Context): TopDatabase {
    return Room.databaseBuilder(context, TopDatabase::class.java, TopDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}
