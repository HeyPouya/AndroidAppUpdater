plugins {
    `kotlin-dsl`
}

group = "com.pouyaheydari.appupdater.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("androidLibraryPlugin") {
            id = "com.pouyaheydari.androidLibraryPlugin"
            implementationClass = "com.pouyaheydari.appupdater.convention.plugins.AndroidLibraryPlugin"
        }
    }
}
