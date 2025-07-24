plugins {
    id("com.example.coffe1706.gradle.android.library")
    alias(libs.plugins.hilt)
    id("com.example.coffe1706.gradle.android.hilt.ksp")
    id("com.example.coffe1706.gradle.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.coffe1706.feature.nearestcoffeeshops"
    androidResources.enable = true
}

kotlin.compilerOptions {
    optIn.addAll(
        "kotlin.time.ExperimentalTime",
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.animation.ExperimentalSharedTransitionApi",
    )
}

dependencies {
    implementation(projects.core.coffee1706CoreModel)
    implementation(projects.core.coffee1706CoreDi)
    implementation(projects.core.coffee1706CoreUi)
    implementation(projects.core.coffee1706CoreUiNavigation)
    implementation(projects.data.coffee1706DataServiceapi)
    implementation(projects.data.coffee1706DataLocation)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.coil.compose)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.yandex.mapkit.mobile)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
