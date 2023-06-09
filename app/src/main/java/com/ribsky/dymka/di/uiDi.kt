package com.ribsky.dymka.di

import com.ribsky.account.dialog.account.AccountViewModel
import com.ribsky.account.dialog.profile.ProfileViewModel
import com.ribsky.auth.ui.AuthViewModel
import com.ribsky.dymka.ui.MainViewModel
import com.ribsky.feed.ui.FeedViewModel
import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.game.manager.connection.ConnectionManagerImpl
import com.ribsky.game.ui.game.GameViewModel
import com.ribsky.game.ui.lobby.LobbyViewModel
import com.ribsky.games.ui.games.GamesViewModel
import com.ribsky.lesson.ui.LessonViewModel
import com.ribsky.lesson.utils.checker.factory.CheckerFactory
import com.ribsky.lesson.utils.checker.factory.CheckerFactoryImpl
import com.ribsky.lessons.dialogs.info.LessonInfoViewModel
import com.ribsky.lessons.ui.LessonsViewModel
import com.ribsky.loader.ui.LoaderViewModel
import com.ribsky.settings.ui.settings.SettingsViewModel
import com.ribsky.share.ui.story.ShareStoryViewModel
import com.ribsky.share.ui.word.ShareWordViewModel
import com.ribsky.shop.ui.ShopViewModel
import com.ribsky.test.ui.TestDetailsViewModel
import com.ribsky.tests.dialogs.info.TestInfoViewModel
import com.ribsky.tests.ui.TestsViewModel
import com.ribsky.top.ui.lessons.TopLessonsViewModel
import com.ribsky.top.ui.tests.TopTestsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {

    viewModel {
        MainViewModel(
            getUserUseCase = get(),
            subManager = get(),
            getRateDialogStatusUseCase = get(),
            dynamicModule = get()
        )
    }

    viewModel {
        AuthViewModel(
            authUseCase = get(),
            clearDataUseCase = get()
        )
    }

    viewModel {
        AccountViewModel(
            getUserUseCase = get(),
            getTestScoreUseCase = get(),
            getActiveLessonsUseCase = get(),
            lessonInteractor = get(),
        )
    }

    viewModel {
        FeedViewModel(
            getBestWordUseCase = get(),
            subManager = get(),
            paragraphInteractor = get(),
        )
    }

    viewModel {
        LessonViewModel(
            getLessonContentUseCase = get(),
            getUserUseCase = get(),
            lessonInteractor = get(),
            chatMapperFactory = get(),
            checkerFactory = get(),
            subManager = get()
        )
    }

    factory<CheckerFactory> {
        CheckerFactoryImpl()
    }

    viewModel {
        LessonsViewModel(
            subManager = get(),
            addActiveLessonUseCase = get(),
            isContentExistsUseCase = get(),
            lessonInteractor = get(),
            paragraphInteractor = get()
        )
    }

    viewModel {
        TopTestsViewModel(
            getUserUseCase = get(),
            getLastTimeUseCase = get(),
            topInteractor = get()
        )
    }

    viewModel {
        TopLessonsViewModel(
            getUserUseCase = get(),
            getLastTimeUseCase = get(),
            topInteractor = get()
        )
    }

    viewModel {
        ShopViewModel(
            subManager = get(),
        )
    }

    viewModel {
        LoaderViewModel(
            loadDataUseCase = get(),
            syncUserUseCase = get(),
            getUserUseCase = get()
        )
    }

    viewModel {
        SettingsViewModel(
            signOutUseCase = get()
        )
    }

    viewModel {
        GamesViewModel(
            getUserUseCase = get(),
            subManager = get(),
            isContentExistsUseCase = get(),
            testInteractor = get()
        )
    }

    viewModel {
        GameViewModel(
            getUserUseCase = get(),
            getTestContentUseCase = get(),
            testInteractor = get()
        )
    }

    viewModel {
        LessonInfoViewModel(
            lessonInteractor = get()
        )
    }

    viewModel {
        ProfileViewModel(
            topInteractor = get(),
            lessonInteractor = get()
        )
    }

    viewModel {
        TestInfoViewModel(
            testInteractor = get()
        )
    }

    viewModel {
        TestsViewModel(
            subManager = get(),
            isContentExistsUseCase = get(),
            testInteractor = get()
        )
    }

    viewModel {
        TestDetailsViewModel(
            getTestContentUseCase = get(),
            getUserUseCase = get(),
            testInteractor = get(),
            subManager = get(),
            addTestScoreUseCase = get(),
            saveWordInteractor = get()
        )
    }

    viewModel {
        ShareWordViewModel(
            bestWordUseCase = get()
        )
    }

    viewModel {
        ShareStoryViewModel()
    }

    viewModel {
        LobbyViewModel()
    }

    single<ConnectionManager> {
        ConnectionManagerImpl(get())
    }
}
