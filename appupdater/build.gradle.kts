import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
    alias(libs.plugins.project.androidLibrary)
}
android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.pouyaheydari.appupdater.main"
}

mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = SourcesJar.Sources(),
            variant = "release",
        ),
    )
}

dependencies {

    api(projects.store)
    api(projects.directdownload)

    // support dependency
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.recyclerView)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.fragment)

    // testing dependency
    testImplementation(libs.junit4)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ui.espresso.core)
}
