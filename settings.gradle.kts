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
include(":core:database")
include(":core:network")
include(":core:common")
include(":core:ui")
include(":feature-cart")
include(":feature-profile")
include(":feature-profile-edit")
include(":feature-profile-changepassword")
include(":feature-catalog")
include(":feature-catalog-categories")
include(":feature-catalog-filter")
include(":feature-catalog-sort")
include(":feature-catalog-product")
include(":feature-auth-signin")
include(":feature-auth-signup-registration")
include(":feature-auth-signup-confirmcode")
include(":feature-auth-signup-complete")
include(":feature-favourites")
include(":feature-order")
