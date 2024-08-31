package com.ribsky.dymka.di

import android.content.Context
import androidx.room.Room
import com.ribsky.data.service.offline.active.ActiveLessonDao
import com.ribsky.data.service.offline.active.ActiveLessonDatabase
import com.ribsky.data.service.offline.article.ArticlesDao
import com.ribsky.data.service.offline.article.ArticlesDatabase
import com.ribsky.data.service.offline.best.BestWordDao
import com.ribsky.data.service.offline.best.BestWordDatabase
import com.ribsky.data.service.offline.lesson.LessonsDao
import com.ribsky.data.service.offline.lesson.LessonsDataBase
import com.ribsky.data.service.offline.notes.NotesDataBase
import com.ribsky.data.service.offline.paragraph.ParagraphDao
import com.ribsky.data.service.offline.paragraph.ParagraphDataBase
import com.ribsky.data.service.offline.stars.StarsLessonDatabase
import com.ribsky.data.service.offline.tests.TestsDao
import com.ribsky.data.service.offline.tests.TestsDataBase
import com.ribsky.data.service.offline.top.TopDatabase
import org.koin.dsl.module

val dbDi = module {

    single { activeBestWordDb(get()) }

    single { lessonsDb(get()) }

    single { testsDb(get()) }

    single { paragraphDb(get()) }

    single { activeDb(get()) }

    single { topDb(get()) }

    single { starsDb(get()) }

    single { notesDb(get()) }

    single { articlesDb(get()) }

    single { bestWordDao(get()) }

    single { lessonsDao(get()) }

    single { testsDao(get()) }

    single { paragraphDao(get()) }

    single { activeDao(get()) }

    single { topDao(get()) }

    single { starsDao(get()) }

    single { notesDao(get()) }

    single { articlesDao(get()) }

}

fun bestWordDao(bestWordDatabase: BestWordDatabase): BestWordDao = bestWordDatabase.dao

fun lessonsDao(lessonsDatabase: LessonsDataBase): LessonsDao = lessonsDatabase.dao

fun testsDao(testsDatabase: TestsDataBase): TestsDao = testsDatabase.dao

fun paragraphDao(paragraphDatabase: ParagraphDataBase): ParagraphDao = paragraphDatabase.dao

fun activeDao(activeLessonDatabase: ActiveLessonDatabase): ActiveLessonDao =
    activeLessonDatabase.dao

fun topDao(topDatabase: TopDatabase) = topDatabase.dao

fun starsDao(starsLessonDatabase: StarsLessonDatabase) = starsLessonDatabase.dao

fun notesDao(notesDataBase: NotesDataBase) = notesDataBase.dao

fun articlesDao(articlesDatabase: ArticlesDatabase) = articlesDatabase.dao

fun activeBestWordDb(context: Context): BestWordDatabase {
    return Room.databaseBuilder(
        context,
        BestWordDatabase::class.java,
        BestWordDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun testsDb(context: Context): TestsDataBase {
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

fun articlesDb(context: Context): ArticlesDatabase {
    return Room.databaseBuilder(
        context,
        ArticlesDatabase::class.java,
        ArticlesDatabase.DATABASE_NAME
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

fun starsDb(context: Context): StarsLessonDatabase {
    return Room.databaseBuilder(
        context,
        StarsLessonDatabase::class.java,
        StarsLessonDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun notesDb(context: Context): NotesDataBase {
    return Room.databaseBuilder(
        context,
        NotesDataBase::class.java,
        NotesDataBase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}


