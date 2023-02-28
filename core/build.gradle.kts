plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.ribsky.core"
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

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":features"))
    implementation(project(":common"))
    implementation(project(":billing"))
    implementation(project(":navigation"))
    implementation(project(":features:main"))
    implementation(project(":features:top"))
    implementation(project(":features:account"))
    implementation(project(":features:games"))
    implementation(project(":features:feed"))
    implementation(project(":features:auth"))
    implementation(project(":features:lessons"))
    implementation(project(":features:shop"))
    implementation(project(":features:loader"))
    implementation(project(":features:settings"))
    implementation(project(":features:share"))
    implementation(project(":features:tests"))
    implementation(project(":features:beta"))
    implementation(project(":features:lesson"))
    implementation(project(":features:test"))
    implementation(project(":features:dialogs"))
    implementation(project(":features:game"))

    kapt(Deps.roomCompiler)

}
