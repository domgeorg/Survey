buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.kspAgp)
        classpath(Dependencies.hiltAgp)
        classpath(Dependencies.kotlinAgp)
    }
}
