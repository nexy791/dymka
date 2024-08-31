package com.ribsky.dymka.di

import com.ribsky.account.dialog.account.AccountViewModel
import com.ribsky.account.dialog.profile.ProfileViewModel
import com.ribsky.article.ui.ArticleViewModel
import com.ribsky.article.ui.fragments.base.BaseArticleViewModel
import com.ribsky.articles.dialogs.info.ArticleInfoViewModel
import com.ribsky.articles.ui.ArticlesViewModel
import com.ribsky.auth.ui.AuthViewModel
import com.ribsky.dymka.ui.MainViewModel
import com.ribsky.feed.ui.FeedViewModel
import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.game.manager.connection.ConnectionManagerImpl
import com.ribsky.game.ui.game.GameViewModel
import com.ribsky.game.ui.lobby.LobbyViewModel
import com.ribsky.games.ui.games.GamesViewModel
import com.ribsky.intro.ui.IntroViewModel
import com.ribsky.intro.ui.fragments.BaseViewModel
import com.ribsky.intro.ui.fragments.from.IntroFromViewModel
import com.ribsky.intro.ui.fragments.goal.IntroGoalViewModel
import com.ribsky.intro.ui.fragments.level.IntroLevelViewModel
import com.ribsky.lesson.ui.LessonViewModel
import com.ribsky.lesson.utils.checker.factory.CheckerFactory
import com.ribsky.lesson.utils.checker.factory.CheckerFactoryImpl
import com.ribsky.lessons.dialogs.info.LessonInfoViewModel
import com.ribsky.lessons.ui.LessonsViewModel
import com.ribsky.loader.ui.LoaderViewModel
import com.ribsky.notes.ui.NotesViewModel
import com.ribsky.paywall.dialogs.paywall.PayWallViewModel
import com.ribsky.settings.ui.settings.SettingsViewModel
import com.ribsky.share.ui.share.ShareStreakViewModel
import com.ribsky.share.ui.story.ShareStoryViewModel
import com.ribsky.share.ui.word.ShareWordViewModel
import com.ribsky.shop.ui.ShopViewModel
import com.ribsky.test.ui.TestDetailsViewModel
import com.ribsky.tests.dialogs.info.TestInfoViewModel
import com.ribsky.tests.ui.TestsViewModel
import com.ribsky.top.ui.stars.TopStarsViewModel
import com.ribsky.top.ui.streak.TopStreakViewModel
import com.ribsky.top.ui.tests.TopTestsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {

    viewModel {
        MainViewModel(
            getUserUseCase = get(),
            subManager = get(),
            getRateDialogStatusUseCase = get(),
            dynamicModule = get(),
            getDiscountUseCase = get(),
            isNeedToFillBioUseCase = get(),
            topInteractor = get()
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
            lessonInteractor = get(),
            getStreakUseCase = get(),
            getLevelByIdUseCase = get(),
            getGoalByIdUseCase = get(),
            isTodayStreakUseCase = get()
        )
    }

    viewModel {
        FeedViewModel(
            getBestWordUseCase = get(),
            subManager = get(),
            paragraphInteractor = get(),
            isTodayStreakUseCase = get(),
            getCurrentStreakUseCase = get(),
            getDiscountUseCase = get(),
            getPromoUseCase = get()
        )
    }

    viewModel {
        LessonViewModel(
            getLessonContentUseCase = get(),
            getUserUseCase = get(),
            lessonInteractor = get(),
            chatMapperFactory = get(),
            checkerFactory = get(),
            subManager = get(),
            addNoteUseCase = get(),
            getDiscountUseCase = get()
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
            paragraphInteractor = get(),
            setTodayStreakUseCase = get(),
            isTodayStreakUseCase = get(),
            getDiscountUseCase = get(),
            addStarsToLessonUseCase = get(),
            topInteractor = get()
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
        TopStarsViewModel(
            getUserUseCase = get(),
            getLastTimeUseCase = get(),
            topInteractor = get()
        )
    }

    viewModel {
        ShopViewModel(
            subManager = get(),
            getDiscountUseCase = get(),
            getUserUseCase = get(),
            topInteractor = get()
        )
    }

    viewModel {
        LoaderViewModel(
            loadDataUseCase = get(),
            syncUserUseCase = get(),
            getUserUseCase = get(),
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
            testInteractor = get(),
            getDiscountUseCase = get()
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
            lessonInteractor = get(),
            getLevelByIdUseCase = get(),
            getGoalByIdUseCase = get(),
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
            testInteractor = get(),
            setTodayStreakUseCase = get(),
            isTodayStreakUseCase = get(),
            getDiscountUseCase = get(),
            topInteractor = get(),
            getTestScoreUseCase = get()
        )
    }

    viewModel {
        TestDetailsViewModel(
            getTestContentUseCase = get(),
            getUserUseCase = get(),
            testInteractor = get(),
            subManager = get(),
            addTestScoreUseCase = get(),
            saveWordInteractor = get(),
            getDiscountUseCase = get()
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

    viewModel {
        BaseViewModel()
    }

    viewModel {
        IntroViewModel(
            getCurrentFromBioUseCase = get(),
            getCurrentGoalBioUseCase = get(),
            getCurrentLevelBioUseCase = get(),
        )
    }

    viewModel {
        TopStreakViewModel(
            getUserUseCase = get(),
            getLastTimeUseCase = get(),
            topInteractor = get()
        )
    }

    viewModel {
        ShareStreakViewModel()
    }

    viewModel {
        IntroGoalViewModel(
            setGoalBioUseCase = get(),
            getGoalsBioUseCase = get()
        )
    }

    viewModel {
        IntroLevelViewModel(
            setLevelBioUseCase = get(),
            getLevelsBioUseCase = get()
        )
    }

    viewModel {
        IntroFromViewModel(
            setFromBioUseCase = get(),
            getFromsBioUseCase = get()
        )
    }

    viewModel {
        NotesViewModel(
            getNotesUseCase = get(),
            deleteNoteUseCase = get(),
            subManager = get(),
            getDiscountUseCase = get()
        )
    }

    viewModel {
        PayWallViewModel(
            topInteractor = get()
        )
    }

    viewModel {
        ArticlesViewModel(
            subManager = get(),
            getDiscountUseCase = get(),
            isContentExistsUseCase = get(),
            getArticlesUseCase = get()
        )
    }

    viewModel {
        ArticleViewModel(
            getArticleContentUseCase = get(),
            getArticleUseCase = get()
        )
    }

    viewModel {
        ArticleInfoViewModel(
            getArticleUseCase = get()
        )
    }

    viewModel {
        BaseArticleViewModel()
    }

    single<ConnectionManager> {
        ConnectionManagerImpl(get())
    }
}
