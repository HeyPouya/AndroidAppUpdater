package com.pouyaheydari.appupdater.convention.helpers

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

fun Project.compileSdk(): Int = baseLibs.findVersion("compileSdkVersion").get().requiredVersion.toInt()

fun Project.minSdk(): Int = baseLibs.findVersion("minSdkVersion").get().requiredVersion.toInt()

val Project.baseLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
