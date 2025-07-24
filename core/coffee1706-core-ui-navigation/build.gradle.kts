plugins {
    id("com.example.coffe1706.gradle.android.library")
    id("com.example.coffe1706.gradle.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.coffe1706.core.ui.navigation"
}

dependencies {
    api(libs.kotlinx.serialization.core)
}
