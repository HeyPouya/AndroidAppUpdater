package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val TENCENT_APP_STORE_URL = "tmast://appdetails?pname="

/**
 * Opens application's page in [Tencent App Store](https://appstore.tencent.com/)
 */
@Parcelize
internal data class TencentAppStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$TENCENT_APP_STORE_URL$packageName")
        .build()

    override fun getType(): AppStoreType = AppStoreType.TENCENT_APPS_STORE

    override fun getUserReadableName(): String = AppStoreType.TENCENT_APPS_STORE.userReadableName
}
