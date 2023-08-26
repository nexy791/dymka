plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.firebase.crashlytics")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.ribsky.dymka"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    setDynamicFeatures(setOf(":features:bot"))
    namespace = "com.ribsky.dymka"
}

dependencies {

    api(project(":data"))
    api(project(":domain"))
    api(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":billing"))
    implementation(project(":features:lesson"))
    implementation(project(":features:games"))
    implementation(project(":features:game"))
    implementation(project(":features:share"))
    implementation(project(":features:share"))
    implementation(project(":features:share"))
    implementation(project(":features:top"))
    implementation(project(":features:test"))
    implementation(project(":features:tests"))
    implementation(project(":features:lessons"))
    implementation(project(":features:settings"))
    implementation(project(":features:settings"))
    implementation(project(":features:account"))
    implementation(project(":features:beta"))
    implementation(project(":features:feed"))
    implementation(project(":features:shop"))
    implementation(project(":features:auth"))
    implementation(project(":features:loader"))
    implementation(project(":features:loader"))
    implementation(project(":features:intro"))
    implementation(project(":features:paywall"))
    implementation(project(":features:notes"))
    implementation(project(":dialogs"))
    implementation(project(":widget"))
    implementation(project(":analytics"))
    implementation(project(mapOf("path" to ":permission")))
}
