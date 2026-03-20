package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val FDROID_URL = "fdroid.app://details?id="
internal const val FDROID_PACKAGE = "org.fdroid.fdroid"

/**
 * Opens application's page in [F-Droid App Store](https://f-droid.org/)
 */
@Parcelize
internal data class FDroid(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$FDROID_URL$packageName")
        .withPackage(FDROID_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.FDROID

    override fun getUserReadableName(): String = AppStoreType.FDROID.userReadableName
}
