pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }
}
rootProject.name = "dymka"
include(
    ":app",
    ":domain",
    ":data",
    ":common",
    ":features",
    ":features:settings",
    ":features:account",
    ":features:loader",
    ":features:auth",
    ":features:share",
    ":features:beta",
    ":features:shop",
    ":analytics",
    ":features:feed",
    ":features:tests",
    ":billing",
    ":features:top",
    ":features:lessons",
    ":features:games"
)

include(":navigation")
include(":dialogs")
include(":features:lesson")
include(":features:test")
include(":permission")
include(":features:game")
include(":widget")
include(":features:bot")
include(":features:intro")
include(":testing")
include(":core")
include(":features:paywall")
include(":libs")
include(":libs:AnimationRatingBar")
