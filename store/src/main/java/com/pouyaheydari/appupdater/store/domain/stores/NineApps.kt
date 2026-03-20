package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val NINE_APPS_STORE_URL = "nineapps://AppDetail?id="
internal const val NINE_APPS_PACKAGE = "com.gamefun.apk2u"

/**
 * Opens application's page in [9-Apps](https://www.9apps.com/)
 */
@Parcelize
internal data class NineApps(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$NINE_APPS_STORE_URL$packageName")
        .withPackage(NINE_APPS_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.NINE_APPS_STORE

    override fun getUserReadableName(): String = AppStoreType.NINE_APPS_STORE.userReadableName
}
