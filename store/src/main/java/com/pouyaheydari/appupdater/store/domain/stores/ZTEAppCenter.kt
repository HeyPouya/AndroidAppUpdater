package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val ZTE_APP_CENTER_URL = "zte_market://appdetails?pname="

/**
 * Opens application's page in [ZTE App Store](https://apps.ztems.com/)
 */
@Parcelize
internal data class ZTEAppCenter(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$ZTE_APP_CENTER_URL$packageName")
        .build()

    override fun getType(): AppStoreType = AppStoreType.ZTE_APP_CENTER

    override fun getUserReadableName(): String = AppStoreType.ZTE_APP_CENTER.userReadableName
}
