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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    namespace = "com.pouyaheydari.appupdater.dsl"
    group = "com.github.sirlordpouya.androidappupdater"
    version = libs.versions.appVersion.get()
}

dependencies {
    // library dependency
    implementation(project(":core"))
    implementation(project(":appupdater"))

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
            artifactId = "dsl"
            version = libs.versions.appVersion.get()
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
