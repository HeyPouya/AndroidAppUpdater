package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val V_APP_STORE_URL = "vivoMarket://details?id="

/**
 * Opens application's page in [V-AppStore](https://developer.vivo.com/home)
 */
@Parcelize
internal data class VAppStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$V_APP_STORE_URL$packageName")
        .build()

    override fun getType(): AppStoreType = AppStoreType.V_APP_STORE

    override fun getUserReadableName(): String = AppStoreType.V_APP_STORE.userReadableName
}
