package com.ribsky.bot.di

import com.ribsky.bot.navigation.BotNavigationImpl
import com.ribsky.bot.ui.BotViewModel
import com.ribsky.navigation.features.BotNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val botDi = module {
    viewModel {
        BotViewModel(
            getUserUseCase = get(),
            remoteConfig = get(),
            subManager = get(),
            addBotScoreUseCase = get(),
            canBotReplyUseCase = get(),
            getBotScoreForTodayUseCase = get(),
            syncUserUseCase = get()
        )
    }
    factory<BotNavigation> {
        BotNavigationImpl()
    }
}