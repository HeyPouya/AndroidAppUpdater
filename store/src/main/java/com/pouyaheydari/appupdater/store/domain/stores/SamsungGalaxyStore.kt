package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val SAMSUNG_GALAXY_STORE_URL = "samsungapps://ProductDetail/"

/**
 * Opens application's page in [Samsung Galaxy store](https://www.samsung.com/de/apps/galaxy-store/)
 */
@Parcelize
internal data class SamsungGalaxyStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$SAMSUNG_GALAXY_STORE_URL$packageName")
        .build()

    override fun getType(): AppStoreType = AppStoreType.SAMSUNG_GALAXY_STORE

    override fun getUserReadableName(): String = AppStoreType.SAMSUNG_GALAXY_STORE.userReadableName
}
