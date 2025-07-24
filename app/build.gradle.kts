plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("com.example.coffe1706.gradle.android.hilt.ksp")
    id("com.example.coffe1706.gradle.android.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.coffe1706"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.coffe1706"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val apiUrl = providers.environmentVariable("COFFEE_1706_API_URL")
            .getOrElse("http://212.41.30.90:35005/")
        buildConfigField("String", "COFFEE_1706_API_URL", "\"$apiUrl\"")

        val yandexMapkitKey = providers.environmentVariable("YANDEX_MAPKIT_MOBILE_KEY")
            .orElse(providers.gradleProperty("yandex.mobileMapKey"))
            .get()
        buildConfigField("String", "YANDEX_MAPKIT_MOBILE_KEY", "\"$yandexMapkitKey\"")
    }

    signingConfigs {
        create("testkey") {
            keyAlias = providers.environmentVariable("SIGN_KEY_ALIAS").getOrElse("testkey")
            keyPassword = providers.environmentVariable("SIGN_KEY_PASSWORD").getOrElse("testkey")
            storeFile = providers.environmentVariable("SIGN_STORE").orNull?.let(::File)
                ?: rootProject.file("test_keystore.jks")
            storePassword = providers.environmentVariable("SIGN_STORE_PASSWORD").getOrElse("testkey")
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("testkey")
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("testkey")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        resValues = true
    }
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
    implementation(projects.data.coffee1706DataShoppingcart)
    implementation(projects.feature.coffee1706FeatureAuth)
    implementation(projects.feature.coffee1706FeatureNearestcoffeeshops)
    implementation(projects.feature.coffee1706FeatureCoffeeshop)

    ksp(libs.hilt.compiler)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.datastore)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.navigation.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
