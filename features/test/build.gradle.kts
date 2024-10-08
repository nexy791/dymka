plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.ribsky.test"
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

    implementation(project(":navigation"))
    implementation(project(":common"))
    implementation(project(":analytics"))
    implementation(project(":domain"))
    implementation(project(":billing"))
    implementation(project(":dialogs"))
    implementation("com.robinhood.ticker:ticker:2.0.4")

}
