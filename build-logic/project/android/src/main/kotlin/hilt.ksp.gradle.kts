package com.example.coffe1706.gradle.android

plugins {
    id("com.google.devtools.ksp")
}

ksp {
    // https://dagger.dev/dev-guide/compiler-options.html
    listOf(
        "fastInit",
        "ignoreProvisionKeyWildcards",
        "strictMultibindingValidation",
        "useBindingGraphFix",
    ).forEach {
        arg("-Adagger.$it", "ENABLED")
    }
}

dependencies {
    ksp(versionCatalog.findLibrary("hilt-compiler").get())
    add("implementation", versionCatalog.findLibrary("hilt").get())
}
