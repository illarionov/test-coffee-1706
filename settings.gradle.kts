pluginManagement {
    includeBuild("build-logic/project")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "coffee_17_06"
include(":app")
include(":core:coffee1706-core-di")
include(":core:coffee1706-core-model")
include(":core:coffee1706-core-ui")
include(":core:coffee1706-core-ui-navigation")
include(":data:coffee1706-data-location")
include(":data:coffee1706-data-serviceapi")
include(":data:coffee1706-data-shoppingcart")
include(":feature:coffee1706-feature-auth")
include(":feature:coffee1706-feature-nearestcoffeeshops")
include(":feature:coffee1706-feature-coffeeshop")
