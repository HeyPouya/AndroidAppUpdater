plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        applicationId = "ir.heydarii.androidappupdater"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.appVersion.get().toInt()
        versionName = libs.versions.appVersion.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    namespace = "ir.heydarii.androidappupdater"
    flavorDimensions.add("type")

    productFlavors {
        create("kotlin")
        create("kotlinDSL"){
            applicationIdSuffix = ".dsl"
        }
    }
}

dependencies {

    //library dependency
    implementation(project(":appupdater"))

    //support dependency
    implementation(libs.appcompat)
    implementation(libs.constraintLayout)

    //testing dependency
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestRules)
    androidTestImplementation(libs.androidTestEspresso)
}
