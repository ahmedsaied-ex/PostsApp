pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.google.dagger.hilt.android") version "2.52"
        id("com.google.devtools.ksp") version "1.9.23-1.0.20"
        id("org.jetbrains.kotlin.kapt") version "1.9.23"
        id("org.jetbrains.kotlin.android") version "1.9.23"
        id("com.android.application") version "8.5.2"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NewMovieApp"
include(":app")
