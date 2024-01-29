plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidLibraryComposeGradlePlugin>()

android {
    namespace = "com.georgiopoulos.core_designsystem"
}

dependencies {
    resources()
    lottie()
}