package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val ONE_STORE_APP_MARKET_URL = "onestore://common/product/"

/**
 * Opens application's page in [OneStore App Market](https://m.onestore.co.kr/mobilepoc/main/main.omp)
 */
@Parcelize
internal data class OneStoreAppMarket(
    val packageName: String,
) : AppStore {
    override fun getIntent() =
        StoreIntentBuilder
            .Builder("$ONE_STORE_APP_MARKET_URL$packageName")
            .build()

    override fun getType(): AppStoreType = AppStoreType.ONE_STORE_APP_MARKET

    override fun getUserReadableName(): String = AppStoreType.ONE_STORE_APP_MARKET.userReadableName
}
