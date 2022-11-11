plugins {
    id("com.android.library")
    id("kotlin-android")
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
            includeBuildTypeValues("release")
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

tasks.register("sourcesJar", Jar::class.java) {
    archiveClassifier.set("sources")
    from("android.sourceSets.main.javaDirectories")
}

artifacts {
    archives(tasks["sourcesJar"])
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

if (android.productFlavors.size > 0) {
    android.libraryVariants.all { variant ->
        if (variant.name.toLowerCase().contains("debug").not()) {

            val bundleTask = tasks["bundle${variant.name.capitalize()}"]

            artifacts {
                archives(bundleTask.path) {
                    classifier = variant.flavorName
                    builtBy(bundleTask)
                    name = project.name
                }
            }
            return@all true
        }
        false
    }
}
