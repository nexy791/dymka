object Deps {

    object Versions {
        const val androidxAppCompat = "1.6.1"
        const val androidxCore = "1.12.0"
        const val material = "1.7.0"

        const val firebaseBom = "32.7.2"
        const val appCheck = "17.1.2"

        const val coroutines = "1.8.0"

        const val room = "2.6.1"

        const val lifecycle = "2.7.0"

        const val koin = "3.5.3"

        const val crypto = "1.1.0-alpha03"

        const val stateDelegator = "1.7"

        const val moshi = "1.15.1"

        const val navigation = "2.7.7"

        const val coil = "2.2.0"
        const val coilFire = "2.0.0-rc03"

        const val splashScreen = "1.0.0"

        const val progressCircle = "3.1.0"

        const val appUpdate = "2.1.0"

        const val recyclerView = "1.3.2"

        const val shimmer = "0.5.0"

        const val konfetti = "2.0.4"

        const val auth = "21.0.0"

        const val kPermissions = "3.4.0"

        const val nearby = "19.1.0"

        const val multiWave = "1.0.0-andx"

        const val rippleView = "v1.0"

        const val avatarView = "1.0.7"

        const val mlKitSmartReply = "17.0.2"
        const val mlKitTranslate = "17.0.1"
        const val openAi = "1.1.5"

        const val loadingDots = "1.3.2"

        const val balloon = "1.6.4"

        const val typeWriter = "v1.3"

        const val delivery = "2.1.0"

        const val deliveryDynamic = "16.0.0-beta2"

        const val mlKit = "18.10.0"

    }

    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val material = "com.google.android.material:material:${Versions.material}"

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebaseDatabase = "com.google.firebase:firebase-database-ktx"
    const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseAppCheck = "com.google.firebase:firebase-appcheck-ktx:${Versions.appCheck}"
    const val firebaseAppCheckPlayIntegrity =
        "com.google.firebase:firebase-appcheck-playintegrity:${Versions.appCheck}"
    const val firebaseRemoteConfig = "com.google.firebase:firebase-config-ktx"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"


    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesPlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"

    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val koin = "io.insert-koin:koin-android:${Versions.koin}"

    // todo update to 1.1.0-alpha06
    const val crypto = "androidx.security:security-crypto:${Versions.crypto}"

    const val stateDelegator = "com.redmadrobot:state-delegator:${Versions.stateDelegator}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"


    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationRuntimeKtx =
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"


    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coil}"
    const val coilFire = "com.github.rosariopfernandes:firecoil:${Versions.coilFire}"

    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

    const val progressCircle = "com.mikhaellopez:circularprogressbar:${Versions.progressCircle}"

    const val appUpdate = "com.google.android.play:app-update:${Versions.appUpdate}"
    const val appUpdateKtx = "com.google.android.play:app-update-ktx:${Versions.appUpdate}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    const val konfetti = "nl.dionsegijn:konfetti-xml:${Versions.konfetti}"

    const val auth = "com.google.android.gms:play-services-auth:${Versions.auth}"

    const val kPermissions = "com.github.fondesa:kpermissions-coroutines:${Versions.kPermissions}"

    const val nearby = "com.google.android.gms:play-services-nearby:${Versions.nearby}"

    const val multiWave = "com.scwang.wave:MultiWaveHeader:${Versions.multiWave}"

    const val rippleView = "com.github.ruzhan123:RippleView:${Versions.rippleView}"

    const val avatarView = "io.getstream:avatarview-coil:${Versions.avatarView}"

    const val mlKitSmartReply = "com.google.mlkit:smart-reply:${Versions.mlKitSmartReply}"
    const val mlKitTranslate = "com.google.mlkit:translate:${Versions.mlKitTranslate}"

    const val openAi = "com.github.mardillu:OpenAI-Client-Android:${Versions.openAi}"

    const val loadingDots = "com.github.razaghimahdi:Android-Loading-Dots:${Versions.loadingDots}"

    const val balloon = "com.github.skydoves:balloon:${Versions.balloon}"

    const val typeWriter = "com.github.NitishGadangi:TypeWriter-TextView:${Versions.typeWriter}"

    const val delivery = "com.google.android.play:feature-delivery:${Versions.delivery}"

    const val deliveryDynamic = "com.google.mlkit:playstore-dynamic-feature-support:${Versions.deliveryDynamic}"

    const val mlKit = "com.google.mlkit:common:${Versions.mlKit}"
}
