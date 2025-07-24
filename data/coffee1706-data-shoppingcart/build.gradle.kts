plugins {
    id("com.example.coffe1706.gradle.android.library")
    id("com.example.coffe1706.gradle.android.protobuf")
}

android {
    namespace = "com.example.coffe1706.data.shoppingcart"
}

dependencies {
    api(projects.core.coffee1706CoreModel)
    api(libs.datastore)
}
