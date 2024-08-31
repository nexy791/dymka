package com.ribsky.dymka.di

import com.ribsky.account.nav.AccountNavigationImpl
import com.ribsky.account.nav.ProfileNavigationImpl
import com.ribsky.article.nav.ArticleNavigationImpl
import com.ribsky.auth.nav.AuthNavigationImpl
import com.ribsky.beta.nav.BetaNavigationImpl
import com.ribsky.dymka.nav.MainNavigationImpl
import com.ribsky.feed.nav.FeedNavigationImpl
import com.ribsky.game.nav.GameNavigationImpl
import com.ribsky.game.nav.LobbyNavigationImpl
import com.ribsky.games.nav.GamesNavigationImpl
import com.ribsky.intro.navigation.IntroNavigationImpl
import com.ribsky.lesson.nav.LessonNavigationImpl
import com.ribsky.lessons.nav.LessonsNavigationImpl
import com.ribsky.loader.nav.LoaderNavigationImpl
import com.ribsky.navigation.features.AccountNavigation
import com.ribsky.navigation.features.ArticleNavigation
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.BetaNavigation
import com.ribsky.navigation.features.BotNavigation
import com.ribsky.navigation.features.BotNavigationImpl
import com.ribsky.navigation.features.FeedNavigation
import com.ribsky.navigation.features.GameNavigation
import com.ribsky.navigation.features.GamesNavigation
import com.ribsky.navigation.features.IntroNavigation
import com.ribsky.navigation.features.LessonNavigation
import com.ribsky.navigation.features.LessonsNavigation
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.LobbyNavigation
import com.ribsky.navigation.features.MainNavigation
import com.ribsky.navigation.features.NotesNavigation
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.navigation.features.ProfileNavigation
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.navigation.features.ShareStoryNavigation
import com.ribsky.navigation.features.ShareStreakNavigation
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.navigation.features.TestsNavigation
import com.ribsky.navigation.features.TopDialogsNavigation
import com.ribsky.navigation.features.TopNavigation
import com.ribsky.notes.nav.NotesNavigationImpl
import com.ribsky.paywall.nav.PayWallNavigationImpl
import com.ribsky.settings.nav.LibraryNavigationImpl
import com.ribsky.settings.nav.SettingsNavigationImpl
import com.ribsky.share.ui.nav.ShareMessageNavigationImpl
import com.ribsky.share.ui.nav.ShareStoryNavigationImpl
import com.ribsky.share.ui.nav.ShareStreakNavigationImpl
import com.ribsky.share.ui.nav.ShareWordNavigationImpl
import com.ribsky.shop.nav.ShopNavigationImpl
import com.ribsky.test.nav.TestNavigationImpl
import com.ribsky.tests.nav.TestsNavigationImpl
import com.ribsky.top.nav.TopDialogsNavigationImpl
import com.ribsky.top.nav.TopNavigationImpl
import org.koin.dsl.module

val navDi = module {

    factory<MainNavigation> {
        MainNavigationImpl()
    }

    factory<AuthNavigation> {
        AuthNavigationImpl()
    }

    factory<LoaderNavigation> {
        LoaderNavigationImpl()
    }

    factory<ShopNavigation> {
        ShopNavigationImpl()
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

    factory<ShareStoryNavigation> {
        ShareStoryNavigationImpl()
    }

    factory<ProfileNavigation> {
        ProfileNavigationImpl()
    }

    factory<LobbyNavigation> {
        LobbyNavigationImpl()
    }

    factory<IntroNavigation> {
        IntroNavigationImpl()
    }

    factory<ShareStreakNavigation> {
        ShareStreakNavigationImpl()
    }

    factory<PayWallNavigation> {
        PayWallNavigationImpl()
    }

    factory<BotNavigation> {
        BotNavigationImpl()
    }

    factory<TopDialogsNavigation> {
        TopDialogsNavigationImpl()
    }

    factory<NotesNavigation> {
        NotesNavigationImpl()
    }

    factory<ArticleNavigation> {
        ArticleNavigationImpl()
    }

}
