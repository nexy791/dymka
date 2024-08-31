package com.ribsky.dymka.di

import com.ribsky.domain.usecase.active.AddActiveLessonUseCase
import com.ribsky.domain.usecase.active.AddActiveLessonUseCaseImpl
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCase
import com.ribsky.domain.usecase.active.GetActiveLessonsUseCaseImpl
import com.ribsky.domain.usecase.article.GetArticleContentUseCase
import com.ribsky.domain.usecase.article.GetArticleContentUseCaseImpl
import com.ribsky.domain.usecase.article.GetArticleUseCase
import com.ribsky.domain.usecase.article.GetArticleUseCaseImpl
import com.ribsky.domain.usecase.article.GetArticlesUseCase
import com.ribsky.domain.usecase.article.GetArticlesUseCaseImpl
import com.ribsky.domain.usecase.auth.AuthUseCase
import com.ribsky.domain.usecase.auth.AuthUseCaseImpl
import com.ribsky.domain.usecase.auth.SignOutUseCase
import com.ribsky.domain.usecase.auth.SignOutUseCaseImpl
import com.ribsky.domain.usecase.best.GetBestWordUseCase
import com.ribsky.domain.usecase.best.GetBestWordUseCaseImpl
import com.ribsky.domain.usecase.bio.GetCurrentFromBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentFromBioUseCaseImpl
import com.ribsky.domain.usecase.bio.GetCurrentGoalBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentGoalBioUseCaseImpl
import com.ribsky.domain.usecase.bio.GetCurrentLevelBioUseCase
import com.ribsky.domain.usecase.bio.GetCurrentLevelBioUseCaseImpl
import com.ribsky.domain.usecase.bio.GetFromsBioUseCase
import com.ribsky.domain.usecase.bio.GetFromsBioUseCaseImpl
import com.ribsky.domain.usecase.bio.GetGoalByIdUseCase
import com.ribsky.domain.usecase.bio.GetGoalByIdUseCaseImpl
import com.ribsky.domain.usecase.bio.GetGoalsBioUseCase
import com.ribsky.domain.usecase.bio.GetGoalsBioUseCaseImpl
import com.ribsky.domain.usecase.bio.GetLevelByIdUseCase
import com.ribsky.domain.usecase.bio.GetLevelByIdUseCaseImpl
import com.ribsky.domain.usecase.bio.GetLevelsBioUseCase
import com.ribsky.domain.usecase.bio.GetLevelsBioUseCaseImpl
import com.ribsky.domain.usecase.bio.IsNeedToFillBioUseCase
import com.ribsky.domain.usecase.bio.IsNeedToFillBioUseCaseImpl
import com.ribsky.domain.usecase.bio.SetFromBioUseCase
import com.ribsky.domain.usecase.bio.SetFromBioUseCaseImpl
import com.ribsky.domain.usecase.bio.SetGoalBioUseCase
import com.ribsky.domain.usecase.bio.SetGoalBioUseCaseImpl
import com.ribsky.domain.usecase.bio.SetLevelBioUseCase
import com.ribsky.domain.usecase.bio.SetLevelBioUseCaseImpl
import com.ribsky.domain.usecase.bot.AddBotScoreUseCase
import com.ribsky.domain.usecase.bot.AddBotScoreUseCaseImpl
import com.ribsky.domain.usecase.bot.CanBotReplyUseCase
import com.ribsky.domain.usecase.bot.CanBotReplyUseCaseImpl
import com.ribsky.domain.usecase.bot.GetBotScoreForTodayUseCase
import com.ribsky.domain.usecase.bot.GetBotScoreForTodayUseCaseImpl
import com.ribsky.domain.usecase.clear.ClearDataUseCase
import com.ribsky.domain.usecase.clear.ClearDataUseCaseImpl
import com.ribsky.domain.usecase.config.GetBotTokenUseCase
import com.ribsky.domain.usecase.config.GetBotTokenUseCaseImpl
import com.ribsky.domain.usecase.config.GetDiscountUseCase
import com.ribsky.domain.usecase.config.GetDiscountUseCaseImpl
import com.ribsky.domain.usecase.config.GetPromoUseCase
import com.ribsky.domain.usecase.config.GetPromoUseCaseImpl
import com.ribsky.domain.usecase.data.LoadDataUseCase
import com.ribsky.domain.usecase.data.LoadDataUseCaseImpl
import com.ribsky.domain.usecase.file.IsContentExistsUseCase
import com.ribsky.domain.usecase.file.IsContentExistsUseCaseImpl
import com.ribsky.domain.usecase.lesson.GetLessonContentUseCase
import com.ribsky.domain.usecase.lesson.GetLessonContentUseCaseImpl
import com.ribsky.domain.usecase.lesson.LessonInteractor
import com.ribsky.domain.usecase.lesson.LessonInteractorImpl
import com.ribsky.domain.usecase.notes.AddNoteUseCase
import com.ribsky.domain.usecase.notes.AddNoteUseCaseImpl
import com.ribsky.domain.usecase.notes.DeleteNoteUseCase
import com.ribsky.domain.usecase.notes.DeleteNoteUseCaseImpl
import com.ribsky.domain.usecase.notes.GetNotesUseCase
import com.ribsky.domain.usecase.notes.GetNotesUseCaseImpl
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
import com.ribsky.domain.usecase.stars.AddStarsToLessonUseCase
import com.ribsky.domain.usecase.stars.AddStarsToLessonUseCaseImpl
import com.ribsky.domain.usecase.stars.GetStarsUseCase
import com.ribsky.domain.usecase.stars.GetStarsUseCaseImpl
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCase
import com.ribsky.domain.usecase.streak.GetCurrentStreakUseCaseImpl
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCase
import com.ribsky.domain.usecase.streak.IsTodayStreakUseCaseImpl
import com.ribsky.domain.usecase.streak.SetTodayStreakUseCase
import com.ribsky.domain.usecase.streak.SetTodayStreakUseCaseImpl
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

    factory<GetPromoUseCase> {
        GetPromoUseCaseImpl(
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

    factory<SetFromBioUseCase> {
        SetFromBioUseCaseImpl(
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

    factory<GetCurrentFromBioUseCase> {
        GetCurrentFromBioUseCaseImpl(
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

    factory<GetFromsBioUseCase> {
        GetFromsBioUseCaseImpl(
            bioRepository = get()
        )
    }

    factory<AddStarsToLessonUseCase> {
        AddStarsToLessonUseCaseImpl(
            activeRepository = get()
        )
    }

    factory<GetStarsUseCase> {
        GetStarsUseCaseImpl(
            activeRepository = get()
        )
    }

    factory<GetNotesUseCase> {
        GetNotesUseCaseImpl(
            notesRepository = get()
        )
    }

    factory<AddNoteUseCase> {
        AddNoteUseCaseImpl(
            notesRepository = get()
        )
    }

    factory<DeleteNoteUseCase> {
        DeleteNoteUseCaseImpl(
            notesRepository = get()
        )
    }

    factory<GetArticlesUseCase> {
        GetArticlesUseCaseImpl(
            articleRepository = get()
        )
    }

    factory<GetArticleContentUseCase> {
        GetArticleContentUseCaseImpl(
            articleRepository = get()
        )
    }

    factory<GetArticleUseCase> {
        GetArticleUseCaseImpl(
            articleRepository = get()
        )
    }


}
