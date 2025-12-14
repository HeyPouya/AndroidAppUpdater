plugins {
    alias(libs.plugins.project.androidLibrary)
}

android {
    namespace = "com.pouyaheydari.appupdater.store"
}

dependencies {
    api(projects.core)

    implementation(libs.androidx.core)

    // testing
    testImplementation(libs.junit4)
    testImplementation(libs.roboelectric)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ui.espresso.core)
    androidTestImplementation(libs.androidx.test.ui.espresso.intents)
}
