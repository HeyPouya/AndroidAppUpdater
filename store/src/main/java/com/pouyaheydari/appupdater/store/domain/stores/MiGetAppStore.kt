package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val MI_APP_STORE_URL = "mimarket://details?id="

/**
 * Opens application's page in [Xiaomi GetApp store](https://global.app.mi.com/)
 */
@Parcelize
internal data class MiGetAppStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() =
        StoreIntentBuilder
            .Builder("$MI_APP_STORE_URL$packageName")
            .build()

    override fun getType(): AppStoreType = AppStoreType.MI_GET_APP_STORE

    override fun getUserReadableName(): String = AppStoreType.MI_GET_APP_STORE.userReadableName
}
