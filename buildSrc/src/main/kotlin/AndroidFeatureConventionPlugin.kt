import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("android-library")
                apply("kotlin-android")
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            android().apply {
                compileSdk = ProjectConfig.compileSdk

                defaultConfig {
                    minSdk = ProjectConfig.minSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = Versions.composeCompiler
                }

                packaging {
                    resources {
                        excludes += "META-INF/*"
                    }
                }
            }

            dependencies {
                hilt()
                lifecycle()
                compose()
                resources()
                domain()
                designSystem()
                navigation()
                unitTest()
                uiTest()
            }
        }
    }
}