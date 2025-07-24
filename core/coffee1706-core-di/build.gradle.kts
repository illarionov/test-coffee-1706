plugins {
    id("com.example.coffe1706.gradle.android.library")
}

android {
    namespace = "com.example.coffe1706.core.di"
}

dependencies {
    implementation(libs.hilt)
}
