package com.example.coffe1706.gradle.android

import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * Convention plugin for use in android library modules
 */
plugins {
    id("com.android.library")
    kotlin("android")
}

extensions.configure<LibraryExtension>("android") {
    compileSdk = versionCatalog.findVersion("compileSdk").get().displayName.toInt()

    defaultConfig {
        namespace = "com.example.coffe1706"
        minSdk = versionCatalog.findVersion("minSdk").get().displayName.toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    explicitApi = ExplicitApiMode.Warning
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}
