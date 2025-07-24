package com.example.coffe1706.gradle.android

import com.android.build.api.dsl.CommonExtension

/**
 * Convention plugin with compose defaults
 */
plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

composeCompiler {
    stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("stability_config.conf"))
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

plugins.withId("com.android.base") {
    extensions.configure(CommonExtension::class.java) {
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        val composeBomDep = platform(
            versionCatalog.findLibrary("androidx-compose-bom").get(),
        )
        add("implementation", composeBomDep)
        add("compileOnly", composeBomDep)
        add("compileOnly", versionCatalog.findLibrary("androidx-compose-runtime").get())
    }
}
