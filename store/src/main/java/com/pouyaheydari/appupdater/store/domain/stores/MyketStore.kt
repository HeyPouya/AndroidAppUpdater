package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val MYKET_URL = "myket://details?id="
internal const val MYKET_PACKAGE = "ir.mservices.market"

/**
 * Opens application's page in [Myket Store](https://myket.ir/)
 */
@Parcelize
internal data class MyketStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$MYKET_URL$packageName")
        .withPackage(MYKET_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.MYKET

    override fun getUserReadableName(): String = AppStoreType.MYKET.userReadableName
}
