plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        applicationId = "com.pouyaheydari.androidappupdater"
        minSdk = libs.versions.composeMinSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.appVersion.get().toInt()
        versionName = libs.versions.appVersion.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    namespace = "com.pouyaheydari.androidappupdater"

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

    // library dependency
    implementation(project(":core"))
    implementation(project(":dsl"))
    implementation(project(":appupdater"))
    implementation(project(":compose"))

    // support dependency
    implementation(libs.appcompat)
    implementation(libs.constraintLayout)
    implementation(libs.lifecycle.runtime)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)

    // testing dependency
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestRules)
    androidTestImplementation(libs.androidTestEspresso)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.test.ui.tooling)
    debugImplementation(libs.compose.test.ui.manifest)
}
