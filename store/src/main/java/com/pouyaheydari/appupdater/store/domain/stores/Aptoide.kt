package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val APTOIDE_URL = "aptoideinstall://package="
internal const val APTOIDE_PACKAGE = "cm.aptoide.pt"

/**
 * Opens application's page in [Aptoide App Store](https://en.aptoide.com/)
 */
@Parcelize
internal data class Aptoide(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$APTOIDE_URL$packageName")
        .withPackage(APTOIDE_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.APTOIDE

    override fun getUserReadableName(): String = AppStoreType.APTOIDE.userReadableName
}
