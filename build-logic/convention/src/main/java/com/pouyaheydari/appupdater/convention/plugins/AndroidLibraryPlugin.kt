package com.pouyaheydari.appupdater.convention.plugins

import com.android.build.gradle.LibraryExtension
import com.pouyaheydari.appupdater.convention.helpers.baseLibs
import com.pouyaheydari.appupdater.convention.helpers.compileSdk
import com.pouyaheydari.appupdater.convention.helpers.javaVersion
import com.pouyaheydari.appupdater.convention.helpers.minSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(baseLibs.findPlugin("androidLibrary").get().get().pluginId)
                apply(baseLibs.findPlugin("jetbrainsKotlinAndroid").get().get().pluginId)
                apply(baseLibs.findPlugin("maven.publish").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = compileSdk()
                defaultConfig {
                    minSdk = minSdk()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                compileOptions {
                    sourceCompatibility = javaVersion()
                    targetCompatibility = javaVersion()
                }
            }
            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain {
                    languageVersion.set(JavaLanguageVersion.of(17))
                }
            }
        }
    }
}
