pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Survey"
include(":app")
include(":core-navigation")
include(":core-data")
include(":core-designsystem")
include(":core-resources")
include(":core-network")
include(":core-domain")
include(":feature-survey")
include(":feature-home")
include(":core-utils")
