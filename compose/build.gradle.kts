plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.composeMinSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    publishing {
        multipleVariants("release") {
            allVariants()
        }
    }
    namespace = "com.pouyaheydari.appupdater.compose"
    group = "com.github.sirlordpouya.androidappupdater"
    version = libs.versions.appVersion.get()

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core"))
    implementation(libs.lifecycle.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.compose.viewModel)

    // testing
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestEspresso)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.test.ui.tooling)
    debugImplementation(libs.compose.test.ui.manifest)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.pouyaheydari.updater"
            artifactId = "compose"
            version = libs.versions.appVersion.get()
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
