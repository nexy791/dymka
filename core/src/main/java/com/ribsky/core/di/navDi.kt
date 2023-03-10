package com.ribsky.core.di

import com.ribsky.account.nav.AccountNavigationImpl
import com.ribsky.auth.nav.AuthNavigationImpl
import com.ribsky.beta.nav.BetaNavigationImpl
import com.ribsky.dialogs.nav.DialogsNavigationImpl
import com.ribsky.feed.nav.FeedNavigationImpl
import com.ribsky.game.nav.GameNavigationImpl
import com.ribsky.games.nav.GamesNavigationImpl
import com.ribsky.lesson.nav.LessonNavigationImpl
import com.ribsky.lessons.nav.LessonsNavigationImpl
import com.ribsky.loader.nav.LoaderNavigationImpl
import com.ribsky.main.nav.MainNavigationImpl
import com.ribsky.navigation.features.*
import com.ribsky.settings.nav.LibraryNavigationImpl
import com.ribsky.settings.nav.SettingsNavigationImpl
import com.ribsky.share.ui.nav.ShareMessageNavigationImpl
import com.ribsky.share.ui.nav.ShareWordNavigationImpl
import com.ribsky.shop.nav.ShopNavigationImpl
import com.ribsky.test.nav.TestNavigationImpl
import com.ribsky.tests.nav.TestsNavigationImpl
import com.ribsky.top.nav.TopNavigationImpl
import org.koin.dsl.module

val navDi = module {

    factory<LoaderNavigation> {
        LoaderNavigationImpl()
    }

    factory<AuthNavigation> {
        AuthNavigationImpl()
    }

    factory<ShopNavigation> {
        ShopNavigationImpl()
    }

    factory<MainNavigation> {
        MainNavigationImpl()
    }

    factory<FeedNavigation> {
        FeedNavigationImpl()
    }

    factory<BetaNavigation> {
        BetaNavigationImpl()
    }

    factory<AccountNavigation> {
        AccountNavigationImpl()
    }

    factory<SettingsNavigation> {
        SettingsNavigationImpl()
    }

    factory<LibraryNavigation> {
        LibraryNavigationImpl()
    }

    factory<LessonsNavigation> {
        LessonsNavigationImpl()
    }

    factory<LessonNavigation> {
        LessonNavigationImpl()
    }

    factory<TestsNavigation> {
        TestsNavigationImpl()
    }

    factory<TestNavigation> {
        TestNavigationImpl()
    }

    factory<TopNavigation> {
        TopNavigationImpl()
    }

    factory<DialogsNavigation> {
        DialogsNavigationImpl()
    }

    factory<ShareWordNavigation> {
        ShareWordNavigationImpl()
    }

    factory<ShareMessageNavigation> {
        ShareMessageNavigationImpl()
    }

    factory<GamesNavigation> {
        GamesNavigationImpl()
    }

    factory<GameNavigation> {
        GameNavigationImpl()
    }
}
