plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
}
android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
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
    publishing {
        multipleVariants("release") {
            allVariants()
        }
    }
    namespace = "ir.heydarii.appupdater"
    group = "com.github.sirlordpouya.androidappupdater"
    version = libs.versions.appVersion.get()

    flavorDimensions.add("type")

    productFlavors {
        create("kotlin")
        create("kotlinDSL")
    }
}

dependencies {

    //support dependency
    implementation(libs.appcompat)
    implementation(libs.constraintLayout)
    implementation(libs.recyclerView)

    //testing dependency
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestRules)
    androidTestImplementation(libs.androidTestEspresso)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.pouyaheydari"
            artifactId = "androidappupdater"
            version = libs.versions.appVersion.get()
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
