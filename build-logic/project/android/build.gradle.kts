plugins {
    `kotlin-dsl`
}

group = "com.example.coffe1706.gradle.android"

dependencies {
    implementation(libs.agp.plugin.api)
    implementation(libs.ksp.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.compiler.plugin)
    implementation(libs.protobuf.plugin)
    runtimeOnly(libs.agp.plugin)
}
