// This build file intentionally uses dotted aliases from the version catalog
// to demonstrate the confusing/"weird" error Gradle emits when resolving them.

plugins {
    // This alias name contains a dot in libs.versions.toml → abba.cabba
    // Using it here tends to produce a confusing error message
    alias(libs.plugins.abba.cabba)
    application
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    // Also try to use the dotted library alias
    implementation(libs.abba.cabba)
}

application {
    mainClass.set("MainKt")
}
