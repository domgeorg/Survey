plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryHiltGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_network"
}

dependencies {
    retrofit()
    moshi()
}