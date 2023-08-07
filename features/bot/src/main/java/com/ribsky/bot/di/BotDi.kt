package com.ribsky.bot.di

import com.ribsky.bot.ui.BotViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val botDi = module {
    viewModel {
        BotViewModel(
            getUserUseCase = get(),
            getBotTokenUseCase = get(),
            subManager = get(),
            addBotScoreUseCase = get(),
            canBotReplyUseCase = get(),
            getBotScoreForTodayUseCase = get(),
            syncUserUseCase = get()
        )
    }
}
