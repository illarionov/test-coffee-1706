plugins {
    id("com.example.coffe1706.gradle.android.library")
    id("com.example.coffe1706.gradle.android.protobuf")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.coffe1706.data.serviceapi"
}

dependencies {
    api(projects.core.coffee1706CoreModel)
    api(libs.okhttp)
    api(libs.datastore)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
}
