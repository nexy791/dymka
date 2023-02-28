plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.ribsky.common"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api(Deps.androidxCore)
    api(Deps.androidxAppCompat)
    api(Deps.material)

    api(Deps.recyclerView)
    api(Deps.shimmer)
    api(Deps.konfetti)

    // Firebase
    api(platform(Deps.firebaseBom))
    api(Deps.firebaseAuth)
    api(Deps.firebaseDatabase)
    api(Deps.firebaseFirestore)
    api(Deps.firebaseStorage)
    api(Deps.firebaseMessaging)
    api(Deps.firebaseCrashlytics)
    api(Deps.firebaseAnalytics)
    api(Deps.firebaseAppCheck)
    api(Deps.firebaseAppCheckPlayIntegrity)

    // Lifecycle
    api(Deps.lifecycleRuntimeKtx)
    api(Deps.lifecycleViewModelKtx)
    api(Deps.lifecycleLiveDataKtx)

    // Coroutines
    api(Deps.coroutinesCore)
    api(Deps.coroutinesAndroid)
    api(Deps.coroutinesPlayServices)

    // Room
    api(Deps.roomRuntime)
    api(Deps.roomKtx)

    // Navigation
    api(Deps.navigationRuntimeKtx)
    api(Deps.navigationFragmentKtx)
    api(Deps.navigationUiKtx)

    kapt(Deps.roomCompiler)

    // Security
    api(Deps.crypto)

    // Koin
    api(Deps.koin)

    // Loading State
    api(Deps.stateDelegator)

    // Moshi
    api(Deps.moshi)
    api(Deps.moshiKotlin)
    api(Deps.moshiAdapters)

    // Coil
    api(Deps.coil)
    api(Deps.coilSvg)
    api(Deps.coilFire)

    // CircleProgress
    api(Deps.progressCircle)

    api(Deps.auth)

    // App Update
    implementation(Deps.appUpdate)
    implementation(Deps.appUpdateKtx)
}
