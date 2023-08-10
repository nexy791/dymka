package com.ribsky.dymka

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.ribsky.common.utils.coil.CustomImageLoader
import com.ribsky.common.utils.sound.SoundHelper
import com.ribsky.dymka.di.billingDi
import com.ribsky.dymka.di.commonDi
import com.ribsky.dymka.di.dataDi
import com.ribsky.dymka.di.dbDi
import com.ribsky.dymka.di.domainUi
import com.ribsky.dymka.di.fireDi
import com.ribsky.dymka.di.mapperDi
import com.ribsky.dymka.di.navDi
import com.ribsky.dymka.di.prefsDi
import com.ribsky.dymka.di.uiDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : SplitCompatApplication(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        initComponents()
    }

    private fun initComponents() {
        initFirebase()
        initKoin()
        initMediaPlayers()
        initCat()
    }

    private fun initFirebase() {
        Firebase.initialize(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    uiDi,
                    domainUi,
                    dataDi,
                    fireDi,
                    prefsDi,
                    dbDi,
                    navDi,
                    billingDi,
                    mapperDi,
                    commonDi
                )
            )
        }
    }

    private fun initMediaPlayers() {
        SoundHelper.setContext(this)
    }

    private fun initCat() {
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(
            PurchasesConfiguration.Builder(this, "goog_zEmHIafiljJfZrJzzFuvKNsTloJ").build()
        )
    }

    override fun newImageLoader(): ImageLoader = CustomImageLoader.build(this)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }


}
