package com.ribsky.dymka.di

import com.ribsky.data.repository.ActiveRepositoryImpl
import com.ribsky.data.repository.AuthRepositoryImpl
import com.ribsky.data.repository.BestWordRepositoryImpl
import com.ribsky.data.repository.BioRepositoryImpl
import com.ribsky.data.repository.BotRepositoryImpl
import com.ribsky.data.repository.ClearRepositoryImpl
import com.ribsky.data.repository.ConfigRepositoryImpl
import com.ribsky.data.repository.DataRepositoryImpl
import com.ribsky.data.repository.FileRepositoryImpl
import com.ribsky.data.repository.LessonRepositoryImpl
import com.ribsky.data.repository.ParagraphRepositoryImpl
import com.ribsky.data.repository.SaveRepositoryImpl
import com.ribsky.data.repository.SettingsRepositoryImpl
import com.ribsky.data.repository.StreakRepositoryImpl
import com.ribsky.data.repository.TopRepositoryImpl
import com.ribsky.data.repository.UserRepositoryImpl
import com.ribsky.data.repository.test.TestRepositoryImpl
import com.ribsky.data.service.config.ConfigService
import com.ribsky.data.service.config.ConfigServiceImpl
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.file.FileServiceImpl
import com.ribsky.data.service.offline.time.TimeService
import com.ribsky.data.service.offline.time.TimeServiceImpl
import com.ribsky.data.service.online.best.BestWordService
import com.ribsky.data.service.online.best.BestWordServiceImpl
import com.ribsky.data.service.online.lesson.LessonService
import com.ribsky.data.service.online.lesson.LessonServiceImpl
import com.ribsky.data.service.online.paragraph.ParagraphService
import com.ribsky.data.service.online.paragraph.ParagraphServiceImpl
import com.ribsky.data.service.online.test.TestService
import com.ribsky.data.service.online.test.TestServiceImpl
import com.ribsky.data.service.online.top.TopService
import com.ribsky.data.service.online.top.TopServiceImpl
import com.ribsky.data.service.user.UserService
import com.ribsky.data.service.user.UserServiceImpl
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.data.utils.crypto.CryptoManagerImpl
import com.ribsky.domain.repository.ActiveRepository
import com.ribsky.domain.repository.AuthRepository
import com.ribsky.domain.repository.BestWordRepository
import com.ribsky.domain.repository.BioRepository
import com.ribsky.domain.repository.BotRepository
import com.ribsky.domain.repository.ClearRepository
import com.ribsky.domain.repository.ConfigRepository
import com.ribsky.domain.repository.DataRepository
import com.ribsky.domain.repository.FileRepository
import com.ribsky.domain.repository.LessonRepository
import com.ribsky.domain.repository.ParagraphRepository
import com.ribsky.domain.repository.SaveRepository
import com.ribsky.domain.repository.SettingsRepository
import com.ribsky.domain.repository.StreakRepository
import com.ribsky.domain.repository.TestRepository
import com.ribsky.domain.repository.TopRepository
import com.ribsky.domain.repository.UserRepository
import org.koin.dsl.module

val dataDi = module {

    single<UserRepository> {
        UserRepositoryImpl(
            userService = get()
        )
    }

    single<UserService> {
        UserServiceImpl(
            database = get(),
            sharedPreferences = get(),
            activeRepository = get(),
            saveRepository = get(),
            subManager = get(),
            internetManager = get(),
            botRepository = get(),
            streakRepository = get(),
            bioRepository = get(),
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get(),
            userService = get(),
        )
    }

    single<LessonRepository> {
        LessonRepositoryImpl(
            lessonService = get(),
            fileService = get(),
            lessonsDao = get(),
            cryptoManager = get(),
            mapper = get()
        )
    }

    factory<LessonService> {
        LessonServiceImpl(
            db = get()
        )
    }

    single<ParagraphRepository> {
        ParagraphRepositoryImpl(
            dao = get(),
            paragraphService = get(),
            mapper = get(),
        )
    }

    factory<ParagraphService> {
        ParagraphServiceImpl(
            db = get()
        )
    }

    single<BestWordRepository> {
        BestWordRepositoryImpl(
            bestWordDao = get(),
            bestWordService = get(),
            mapper = get()
        )
    }

    factory<BestWordService> {
        BestWordServiceImpl(
            db = get(),
            fileService = get(),
            cryptoManager = get(),
            sharedPreferences = get(),
            bestWordDao = get()
        )
    }

    factory<CryptoManager> {
        CryptoManagerImpl(context = get())
    }

    single<TestRepository> {
        TestRepositoryImpl(
            testsDao = get(),
            cryptoManager = get(),
            fileService = get(),
            testService = get(),
            mapper = get()
        )
    }

    factory<TestService> {
        TestServiceImpl(
            db = get()
        )
    }

    single<TopRepository> {
        TopRepositoryImpl(
            topDao = get(),
            topService = get(),
            topMapper = get()
        )
    }

    factory<TopService> {
        TopServiceImpl(
            db = get()
        )
    }

    single<FileRepository> {
        FileRepositoryImpl(
            fileService = get()
        )
    }

    single<FileService> {
        FileServiceImpl(
            context = get(),
            storage = get(),
            internetManager = get(),
            cryptoManager = get()
        )
    }

    single<DataRepository> {
        DataRepositoryImpl(
            lessonsRepository = get(),
            testRepository = get(),
            paragraphRepository = get(),
            bestWordRepository = get(),
            topRepository = get(),
            internetManager = get(),
            timeService = get()
        )
    }

    single<ClearRepository> {
        ClearRepositoryImpl(
            firebaseAuth = get(),
            sharedPreferences = get(),
            fileService = get(),
            bestWordDao = get(),
            lessonsDao = get(),
            testsDao = get(),
            paragraphDao = get(),
            activeLessonDao = get()
        )
    }

    factory<SettingsRepository> {
        SettingsRepositoryImpl(
            sharedPreferences = get(),
            timeService = get()
        )
    }

    single<ActiveRepository> {
        ActiveRepositoryImpl(
            sharedPreferences = get(),
            activeLessonDao = get(),
            starsLessonDao = get()
        )
    }

    single<SaveRepository> {
        SaveRepositoryImpl(
            sharedPreferences = get(),
        )
    }

    single<TimeService> {
        TimeServiceImpl(
            sharedPreferences = get(),
        )
    }

    single<BotRepository> {
        BotRepositoryImpl(
            sharedPreferences = get(),
            subManager = get()
        )
    }

    single<ConfigRepository> {
        ConfigRepositoryImpl(
            configService = get()
        )
    }

    single<ConfigService> {
        ConfigServiceImpl(
            remoteConfig = get()
        )
    }

    single<StreakRepository> {
        StreakRepositoryImpl(
            sharedPreferences = get()
        )
    }

    single<BioRepository> {
        BioRepositoryImpl(
            sharedPreferences = get(),
            goalMapper = get(),
            levelMapper = get()
        )
    }
}
