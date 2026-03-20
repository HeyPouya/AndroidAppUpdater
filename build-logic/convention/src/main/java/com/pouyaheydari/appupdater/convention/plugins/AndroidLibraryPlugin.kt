package com.pouyaheydari.appupdater.convention.plugins

import com.android.build.api.dsl.LibraryExtension
import com.pouyaheydari.appupdater.convention.helpers.baseLibs
import com.pouyaheydari.appupdater.convention.helpers.compileSdk
import com.pouyaheydari.appupdater.convention.helpers.javaVersion
import com.pouyaheydari.appupdater.convention.helpers.minSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(baseLibs.findPlugin("androidLibrary").get().get().pluginId)
                apply(baseLibs.findPlugin("kotlin.parcelize").get().get().pluginId)
                apply(baseLibs.findPlugin("maven.publish").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = compileSdk()
                defaultConfig {
                    minSdk = minSdk()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                compileOptions {
                    sourceCompatibility = javaVersion()
                    targetCompatibility = javaVersion()
                }
            }
        }
    }
}
