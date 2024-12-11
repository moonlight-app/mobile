pluginManagement {
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

rootProject.name = "Moonlight"
include(":app")
include(":core:domain")
include(":core:domain-model")
include(":core:data")
include(":core:datastore")
include(":core:network")
include(":core:common")
include(":core:ui")
include(":feature-auth")
include(":feature-cart")
include(":feature-profile")
include(":feature-profile-edit")
include(":feature-catalog")
include(":feature-catalog-categories")