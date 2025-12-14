plugins {
    alias(libs.plugins.project.androidLibrary)
}

android {
    namespace = "com.pouyaheydari.appupdater.directdownload"
}

dependencies {
    api(projects.core)

    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.coroutines)

    // testing
    testImplementation(libs.junit4)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.roboelectric)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ui.espresso.core)
    androidTestImplementation(libs.androidx.test.ui.espresso.intents)
}
