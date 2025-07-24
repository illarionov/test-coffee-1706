plugins {
    id("com.example.coffe1706.gradle.android.library")
    id("com.example.coffe1706.gradle.android.compose")
}

android {
    namespace = "com.example.coffe1706.core.ui"
    androidResources.enable = true
}

dependencies {
    api(projects.core.coffee1706CoreModel)
    api(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)
}
