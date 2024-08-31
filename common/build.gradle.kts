
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api(project(":core"))
    api(project(":libs:AnimationRatingBar"))

    api(Deps.androidxCore)
    api(Deps.androidxAppCompat)
    api(Deps.material)

    api(Deps.recyclerView)
    api(Deps.shimmer)
    api(Deps.konfetti)

    // Firebase
    api(Deps.firebaseAuth)
    api(Deps.firebaseDatabase)
    api(Deps.firebaseFirestore)
    api(Deps.firebaseStorage)
    api(Deps.firebaseMessaging)
    api(Deps.firebaseCrashlytics)
    api(Deps.firebaseAnalytics)
    api(Deps.firebaseAppCheck)
    api(Deps.firebaseAppCheckPlayIntegrity)
    api(Deps.firebaseRemoteConfig)
    api(Deps.auth)
    api(Deps.firebaseStorageUi)


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
    implementation(project(":billing"))
    implementation(project(":domain"))

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

    // Glide
    api(Deps.glide)
    api("com.github.qoqa:glide-svg:4.0.2")
    kapt(Deps.glideAnnotation)

    // CircleProgress
    api(Deps.progressCircle)

    // App Update
    implementation(Deps.appUpdate)
    implementation(Deps.appUpdateKtx)

    implementation(Deps.splashScreen)
    api("dev.chrisbanes.insetter:insetter:0.6.1")

    api(Deps.delivery)
    api(Deps.mlKit)
    api(Deps.deliveryDynamic) {
        exclude(group = "com.google.android.play", module = "core")
    }


    api(Deps.balloon)
}
