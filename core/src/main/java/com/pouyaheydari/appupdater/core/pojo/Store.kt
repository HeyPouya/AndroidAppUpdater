package com.pouyaheydari.appupdater.core.pojo

import com.pouyaheydari.appupdater.core.stores.AmazonAppStore
import com.pouyaheydari.appupdater.core.stores.Aptoide
import com.pouyaheydari.appupdater.core.stores.CafeBazaarStore
import com.pouyaheydari.appupdater.core.stores.GooglePlayStore
import com.pouyaheydari.appupdater.core.stores.HuaweiAppGallery
import com.pouyaheydari.appupdater.core.stores.IranAppsStore
import com.pouyaheydari.appupdater.core.stores.MyketStore
import com.pouyaheydari.appupdater.core.stores.SamsungGalaxyStore
import com.pouyaheydari.appupdater.core.stores.Stores

/**
 * Enum class to select type of market
 */
enum class Store(val provider: Class<out Stores>?) {
    DIRECT_URL(null),
    GOOGLE_PLAY(GooglePlayStore::class.java),
    CAFE_BAZAAR(CafeBazaarStore::class.java),
    MYKET(MyketStore::class.java),
    IRAN_APPS(IranAppsStore::class.java),
    HUAWEI_APP_GALLERY(HuaweiAppGallery::class.java),
    SAMSUNG_GALAXY_STORE(SamsungGalaxyStore::class.java),
    AMAZON_APP_STORE(AmazonAppStore::class.java),
    APTOIDE(Aptoide::class.java),
}
