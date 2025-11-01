plugins {
    // Working alias from the version catalog
    alias(libs.plugins.kotlin.jvm)
    application
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.datetime)
}

application {
    // Fully qualified name or Top-level Kotlin file with main()
    mainClass.set("MainKt")
}

// Convenience task to run the app via `./gradlew run`
tasks.wrapper {
    gradleVersion = "8.10.2"
}
