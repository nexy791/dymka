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
import com.ribsky.common.utils.coil.CustomImageLoader
import com.ribsky.dymka.di.*
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

    override fun newImageLoader(): ImageLoader = CustomImageLoader.build(this)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}
