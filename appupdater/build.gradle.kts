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

    publishing {
        multipleVariants("release") {
            allVariants()
        }
    }
    namespace = "com.pouyaheydari.appupdater"
    group = "com.github.sirlordpouya.androidappupdater"
    version = libs.versions.appVersion.get()
}

dependencies {

    implementation(project(":core"))
    // support dependency
    implementation(libs.appcompat)
    implementation(libs.constraintLayout)
    implementation(libs.recyclerView)

    // testing dependency
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestRules)
    androidTestImplementation(libs.androidTestEspresso)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.pouyaheydari.updater"
            artifactId = "main"
            version = libs.versions.appVersion.get()
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
