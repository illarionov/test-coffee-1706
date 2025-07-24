package com.example.coffe1706.gradle.android

import com.google.protobuf.gradle.id

/**
 * Convention plugin that configures protobuf
 */
plugins {
    id("com.google.protobuf")
}

protobuf {
    protoc {
        artifact = versionCatalog.findLibrary("protobuf-protoc").get().get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    add(
        "implementation",
        versionCatalog.findLibrary("protobuf-kotlin-lite").get(),
    )
}

