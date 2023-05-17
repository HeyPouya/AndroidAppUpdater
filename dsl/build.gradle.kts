plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
}
android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    namespace = "com.pouyaheydari.appupdater.dsl"
}

dependencies {
    // library dependency
    api(project(":core"))
    implementation(project(":appupdater"))

    // testing dependency
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidTestJUnit)
    androidTestImplementation(libs.androidTestRules)
    androidTestImplementation(libs.androidTestEspresso)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.pouyaheydari.updater"
                artifactId = "dsl"
                version = libs.versions.appVersion.get()
                afterEvaluate {
                    from(components["release"])
                }
            }
        }
    }
}
