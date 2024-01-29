plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryHiltGradlePlugin>()
apply<AndroidLibraryComposeGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_navigation"
}

dependencies {
    composeNavigation()
}