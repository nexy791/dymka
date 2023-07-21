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

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":app"))
    implementation(project(":common"))
    implementation(project(":navigation"))
    implementation(project(":analytics"))
    implementation(project(":domain"))
    implementation(project(":permission"))
    implementation(project(":billing"))
    implementation(project(":dialogs"))

    implementation(Deps.mlKitSmartReply)
    implementation(Deps.mlKitTranslate)
    implementation(Deps.loadingDots)
    implementation(Deps.openAi) {
        exclude("com.google.android.material", "material")
    }

}
