# Kotlin Hello World + Version Catalog reproducer

This project is a minimal Kotlin/JVM "Hello, world" application that uses Gradle's Version Catalogs (`gradle/libs.versions.toml`). It also demonstrates a "weird"/confusing error message that occurs when a library or plugin alias in the catalog contains a dot (`.`) on the left side (e.g. `abba.cabba`).

## What this project contains
- Simple Kotlin app that prints the current time using `kotlinx-datetime`.
- Working aliases in `gradle/libs.versions.toml` (e.g. `kotlinx-datetime`, `kotlin-jvm`).
- Intentionally broken aliases that contain dots (e.g. `abba.cabba`) under both `[libraries]` and `[plugins]` sections to illustrate the problem.

## Prereqs
- JDK 17+
- Internet access to download dependencies

## Run the working build
```
./gradlew run
```
Expected output (example):
```
> Task :run
Hello, world! It is 2025-11-01T23:21:42.123[...]
```

## Reproduce the "weird error" with dotted aliases
There is an alternative build script that tries to use the dotted aliases from the version catalog.
Run it with the `-b` flag:
```
./gradlew -b build-dotted.gradle.kts run
```
You should see a confusing error related to resolving `libs.plugins.abba.cabba` and/or `libs.abba.cabba`. Depending on Gradle version, the message can look like one of:
- Could not get unknown property 'libs' for PluginDependenciesSpec of type org.gradle.plugin.use.internal.DefaultPluginRequestCollector.
- No signature of method: PluginDependenciesSpec.alias() is applicable...
- Failed to apply plugin 'org.jetbrains.kotlin.jvm' (when resolution happens in a later phase)

The root cause is that alias names in a Version Catalog must not contain dots. When a dotted name is present on the left side in `libs.versions.toml`, Gradle generates type-safe accessors that become ambiguous or malformed, yielding non-obvious error messages when referenced.

## Files of interest
- `gradle/libs.versions.toml` – contains both working aliases and the intentionally dotted aliases (`abba.cabba`).
- `build.gradle.kts` – normal working Kotlin/JVM app using catalog aliases.
- `build-dotted.gradle.kts` – tries to use the dotted aliases to provoke the error.
- `src/main/kotlin/Main.kt` – minimal app that prints a timestamp.

## Workarounds / Notes
- Do not use dots in alias names in Version Catalogs. Prefer letters, digits, and dashes/underscores (e.g. `abba_cabba` or `abba-cabba`).
- If you need grouping, use prefixing like `abbaCabbaxxx` or nested tables in TOML without dots in the alias keys themselves.
