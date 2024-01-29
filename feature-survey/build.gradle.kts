plugins {
    `android-library`
    `kotlin-android`
}

apply<AndroidFeatureConventionPlugin>()

android {
    namespace = "com.georgiopoulos.feature_survey"
}

dependencies {
    lottie()
    utils()
}