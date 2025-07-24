plugins {
    id("com.example.coffe1706.gradle.android.library")
}

android {
    namespace = "com.example.coffe1706.data.location"
}

dependencies {
    api(projects.core.coffee1706CoreModel)
    implementation(libs.google.play.services.location)
}
