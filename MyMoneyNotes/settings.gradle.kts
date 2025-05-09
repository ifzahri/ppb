pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.9.23"
        kotlin("kapt") version "1.9.23"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {

        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MyMoney Notes"
include(":app")