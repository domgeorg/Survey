import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val composeMaterial = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val kspAgp = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${Versions.ksp}"
    const val kotlinAgp = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"

    const val androidLifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidLifecycle}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val testCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val androidxArchCore = "androidx.arch.core:core-testing:${Versions.androidxArchCore}"

    const val composeUiJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val testManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.moshi() {
    implementation(Dependencies.moshiCodegen)
    ksp(Dependencies.moshiCodegen)
}

fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeMaterial)
    debugImplementation(Dependencies.composeUiToolingPreview)
}

fun DependencyHandler.composeNavigation() {
    implementation(Dependencies.composeNavigation)
}

fun DependencyHandler.coreKtx() {
    implementation(Dependencies.coreKtx)
}

fun DependencyHandler.activityCompose() {
    implementation(Dependencies.activityCompose)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)
}

fun DependencyHandler.hiltNavigationCompose() {
    implementation(Dependencies.hiltNavigationCompose)
}

fun DependencyHandler.lottie() {
    implementation(Dependencies.lottie)
}

fun DependencyHandler.lifecycle() {
    implementation(Dependencies.androidLifecycle)
}

fun DependencyHandler.unitTest() {
    test(Dependencies.junit)
    implementation(Dependencies.mockk)
    implementation(Dependencies.testCoroutines)
    implementation(Dependencies.androidxArchCore)
}

fun DependencyHandler.uiTest() {
    androidTest(Dependencies.composeUiJunit4)
    debugImplementation(Dependencies.testManifest)
}

fun DependencyHandler.network() {
    implementation(project(":core-network"))
}

fun DependencyHandler.domain() {
    implementation(project(":core-domain"))
}

fun DependencyHandler.data() {
    implementation(project(":core-data"))
}

fun DependencyHandler.designSystem() {
    implementation(project(":core-designsystem"))
}

fun DependencyHandler.resources() {
    implementation(project(":core-resources"))
}

fun DependencyHandler.utils() {
    implementation(project(":core-utils"))
}

fun DependencyHandler.navigation() {
    composeNavigation()
    implementation(project(":core-navigation"))
}

fun DependencyHandler.featureSurvey() {
    implementation(project(":feature-survey"))
}

fun DependencyHandler.featureHome() {
    implementation(project(":feature-home"))
}
