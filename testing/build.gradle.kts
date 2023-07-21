plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ribsky.testing"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(Deps.androidxCore)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.material)
    implementation(Deps.firebaseRemoteConfig)

    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:truth:1.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // mockk
    testImplementation("io.mockk:mockk:1.12.0")
    androidTestImplementation("io.mockk:mockk-android:1.12.0")

    // coroutines test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")


    androidTestImplementation(project(":analytics"))
    androidTestImplementation(project(":permission"))
    androidTestImplementation(project(":core"))
    androidTestImplementation(project(":common"))
    androidTestImplementation(project(":data"))
    androidTestImplementation(project(":billing"))
    androidTestImplementation(project(":features:bot"))
    androidTestImplementation(project(":domain"))

}