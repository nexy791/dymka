package com.ribsky.dymka.di

import com.ribsky.domain.usecase.active.AddActiveLessonUseCase
import com.ribsky.domain.usecase.active.AddActiveLessonUseCaseImpl
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCase
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCaseImpl
import com.ribsky.domain.usecase.auth.AuthUseCase
import com.ribsky.domain.usecase.auth.AuthUseCaseImpl
import com.ribsky.domain.usecase.auth.SignOutUseCase
import com.ribsky.domain.usecase.auth.SignOutUseCaseImpl
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import com.ribsky.domain.usecase.best.GetBestWordUseCaseImpl
import com.ribsky.domain.usecase.bio.*
import com.ribsky.domain.usecase.bot.*
import com.ribsky.domain.usecase.clear.ClearDataUseCase
import com.ribsky.domain.usecase.clear.ClearDataUseCaseImpl
import com.ribsky.domain.usecase.config.GetBotTokenUseCase
import com.ribsky.domain.usecase.config.GetBotTokenUseCaseImpl
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCaseImpl
import com.ribsky.domain.usecase.data.LoadDataUseCase
import com.ribsky.domain.usecase.data.LoadDataUseCaseImpl
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCaseImpl
import com.ribsky.domain.usecase.lesson.GetLessonContentUseCase
import com.ribsky.domain.usecase.lesson.GetLessonContentUseCaseImpl
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.lesson.LessonInteractorImpl
import com.ribsky.domain.usecase.paragraph.ParagraphInteractor
import com.ribsky.domain.usecase.paragraph.ParagraphInteractorImpl
import com.ribsky.domain.usecase.save.SaveWordInteractor
import com.ribsky.domain.usecase.save.SaveWordInteractorImpl
import com.ribsky.domain.usecase.score.AddTestScoreUseCase
import com.ribsky.domain.usecase.score.AddTestScoreUseCaseImpl
import com.ribsky.domain.usecase.score.GetTestScoreUseCase
import com.ribsky.domain.usecase.score.GetTestScoreUseCaseImpl
import com.ribsky.domain.usecase.sp.GetRateDialogStatusUseCase
import com.ribsky.domain.usecase.sp.GetRateDialogStatusUseCaseImpl
import com.ribsky.domain.usecase.streak.*
import com.ribsky.domain.usecase.test.GetTestContentUseCase
import com.ribsky.domain.usecase.test.GetTestContentUseCaseImpl
import com.ribsky.domain.usecase.test.TestInteractor
import com.ribsky.domain.usecase.test.TestInteractorImpl
import com.ribsky.domain.usecase.time.GetLastTimeUseCase
import com.ribsky.domain.usecase.time.GetLastTimeUseCaseImpl
import com.ribsky.domain.usecase.top.TopInteractor
import com.ribsky.domain.usecase.top.TopInteractorImpl
import com.ribsky.domain.usecase.user.GetUserUseCase
import com.ribsky.domain.usecase.user.GetUserUseCaseImpl
import com.ribsky.domain.usecase.user.SyncUserUseCase
import com.ribsky.domain.usecase.user.SyncUserUseCaseImpl
import org.koin.dsl.module

val domainUi = module {

    factory<GetUserUseCase> {
        GetUserUseCaseImpl(
            userRepository = get(),
            authRepository = get()
        )
    }

    factory<AuthUseCase> {
        AuthUseCaseImpl(
            authRepository = get()
        )
    }

    factory<GetLessonContentUseCase> {
        GetLessonContentUseCaseImpl(
            lessonRepository = get()
        )
    }

    factory<TestInteractor> {
        TestInteractorImpl(
            testRepository = get()
        )
    }

    factory<GetTestContentUseCase> {
        GetTestContentUseCaseImpl(
            testRepository = get()
        )
    }

    factory<GetBestWordUseCase> {
        GetBestWordUseCaseImpl(
            bestWordRepository = get()
        )
    }

    factory<LoadDataUseCase> {
        LoadDataUseCaseImpl(
            dataRepository = get()
        )
    }

    factory<SignOutUseCase> {
        SignOutUseCaseImpl(
            authRepository = get()
        )
    }

    factory<GetTestScoreUseCase> {
        GetTestScoreUseCaseImpl(
            activeRepository = get()
        )
    }

    factory<AddTestScoreUseCase> {
        AddTestScoreUseCaseImpl(
            activeRepository = get()
        )
    }
    factory<ClearDataUseCase> {
        ClearDataUseCaseImpl(
            clearRepository = get()
        )
    }
    factory<GetActiveLessonsUseCase> {
        GetActiveLessonsUseCaseImpl(
            activeRepository = get()
        )
    }
    factory<AddActiveLessonUseCase> {
        AddActiveLessonUseCaseImpl(
            activeRepository = get()
        )
    }
    factory<SyncUserUseCase> {
        SyncUserUseCaseImpl(
            userRepository = get(),
            authRepository = get()
        )
    }

    factory<SaveWordInteractor> {
        SaveWordInteractorImpl(
            saveRepository = get()
        )
    }

    factory<GetLastTimeUseCase> {
        GetLastTimeUseCaseImpl(
            settingsRepository = get()
        )
    }

    factory<GetRateDialogStatusUseCase> {
        GetRateDialogStatusUseCaseImpl(
            settingsRepository = get()
        )
    }

    factory<IsContentExistsUseCase> {
        IsContentExistsUseCaseImpl(
            fileRepository = get()
        )
    }

    factory<TopInteractor> {
        TopInteractorImpl(
            repository = get()
        )
    }

    factory<LessonInteractor> {
        LessonInteractorImpl(
            lessonsRepository = get(),
            activeRepository = get()
        )
    }

    factory<ParagraphInteractor> {
        ParagraphInteractorImpl(
            paragraphRepository = get(),
            activeRepository = get(),
            lessonRepository = get()
        )
    }

    factory<AddBotScoreUseCase> {
        AddBotScoreUseCaseImpl(
            botRepository = get()
        )
    }

    factory<GetBotScoreForTodayUseCase> {
        GetBotScoreForTodayUseCaseImpl(
            botRepository = get()
        )
    }

    factory<CanBotReplyUseCase> {
        CanBotReplyUseCaseImpl(
            botRepository = get()
        )
    }

    factory<GetDiscountUseCase> {
        GetDiscountUseCaseImpl(
            configRepository = get()
        )
    }

    factory<GetBotTokenUseCase> {
        GetBotTokenUseCaseImpl(
            configRepository = get()
        )
    }

    factory<IsTodayStreakUseCase> {
        IsTodayStreakUseCaseImpl(
            streakRepository = get()
        )
    }

    factory<GetCurrentStreakUseCase> {
        GetCurrentStreakUseCaseImpl(
            streakRepository = get()
        )
    }

    factory<SetTodayStreakUseCase> {
        SetTodayStreakUseCaseImpl(
            streakRepository = get()
        )
    }

    factory<SetLevelBioUseCase> {
        SetLevelBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<SetGoalBioUseCase> {
        SetGoalBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetLevelsBioUseCase> {
        GetLevelsBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetGoalsBioUseCase> {
        GetGoalsBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<IsNeedToFillBioUseCase> {
        IsNeedToFillBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetCurrentLevelBioUseCase> {
        GetCurrentLevelBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetCurrentGoalBioUseCase> {
        GetCurrentGoalBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetGoalByIdUseCase> {
        GetGoalByIdUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<GetLevelByIdUseCase> {
        GetLevelByIdUseCaseImpl(
            bioRepository = get()
        )
    }


}
