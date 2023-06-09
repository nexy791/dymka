package com.ribsky.dymka.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module

val fireDi = module {

    single { Firebase.auth }

    single {
        Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                isPersistenceEnabled = true
                cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
            }
        }
    }

    single {
        Firebase.storage
    }

    single {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setFetchTimeoutInSeconds(43200) // 12h
                    .build()
            )
        }
    }

    single {
        Firebase.crashlytics
    }

    single { Firebase.database("https://dymka-ua-default-rtdb.europe-west1.firebasedatabase.app/") }
}
