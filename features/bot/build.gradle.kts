plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.dynamic-feature")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.ribsky.bot"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
    }

    buildTypes {
        release {
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

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":app"))
    implementation(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":analytics"))
    implementation(project(":domain"))
    implementation(Deps.openAi) {
        exclude("com.google.android.material", "material")
    }
    implementation(project(":permission"))
    implementation(project(":billing"))
    implementation(project(":dialogs"))

    implementation(Deps.mlKitSmartReply)
    implementation(Deps.mlKitTranslate)
    implementation(Deps.loadingDots)
}
