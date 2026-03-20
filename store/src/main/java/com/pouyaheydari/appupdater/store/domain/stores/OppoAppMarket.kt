package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val OPPO_APP_MARKET_URL = "market://details?id="
internal const val OPPO_PACKAGE = "com.heytap.market"

/**
 * Opens application's page in [OppoAppMarket](https://oppomobile.com/)
 */
@Parcelize
internal data class OppoAppMarket(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$OPPO_APP_MARKET_URL$packageName")
        .withPackage(OPPO_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.OPPO_APP_MARKET

    override fun getUserReadableName(): String = AppStoreType.OPPO_APP_MARKET.userReadableName
}
