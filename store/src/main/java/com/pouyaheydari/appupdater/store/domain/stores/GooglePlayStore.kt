package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val PLAY_URL = "market://details?id="
internal const val PLAY_PACKAGE = "com.android.vending"

/**
 * Opens application's page in [GooglePlay Store](https://play.google.com)
 */
@Parcelize
internal data class GooglePlayStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$PLAY_URL$packageName")
        .withPackage(PLAY_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.GOOGLE_PLAY

    override fun getUserReadableName(): String = AppStoreType.GOOGLE_PLAY.userReadableName
}
