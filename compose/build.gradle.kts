plugins {
    alias(libs.plugins.project.androidLibrary)
    alias(libs.plugins.compose.compiler)
}

android {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    namespace = "com.pouyaheydari.appupdater.compose"
    testOptions.unitTests.isIncludeAndroidResources = true

    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    api(projects.store)
    api(projects.directdownload)

    // compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.viewModel)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle)

    // testing and preview
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.junit4)
    testImplementation(libs.androidx.compose.ui.test.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.roboelectric)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.ui.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.uiautomator)
}
