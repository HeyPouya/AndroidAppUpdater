package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val LENOVO_APP_CENTER_URL = "leapp://ptn/appinfo.do?pn="

/**
 * Opens application's page in [Lenovo App Store](https://www.lenovomm.com/)
 */
@Parcelize
internal data class LenovoAppCenter(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$LENOVO_APP_CENTER_URL$packageName")
        .build()

    override fun getType(): AppStoreType = AppStoreType.LENOVO_APP_CENTER

    override fun getUserReadableName(): String = AppStoreType.LENOVO_APP_CENTER.userReadableName
}
