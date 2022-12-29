package com.pouyaheydari.appupdater.compose.utils

import com.pouyaheydari.appupdater.compose.R
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val CUSTOM_URL = "https://cafebazaar.ir/download/bazaar.apk"
private const val SAMPLE_PACKAGE_NAME = "com.tencent.mm"
private const val FDROID_SAMPLE_PACKAGE_NAME = "de.storchp.fdroidbuildstatus"
private const val GET_APP_SAMPLE_PACKAGE_NAME = "com.opera.browser"
private const val ONE_STORE_SAMPLE_PACKAGE_NAME = "com.kakao.talk"

val storeList = listOf(
    // direct download
    UpdaterStoreList(
        store = Store.DIRECT_URL,
        title = "Direct Download 1",
        icon = R.drawable.appupdater_ic_cloud,
        url = CUSTOM_URL,
        packageName = SAMPLE_PACKAGE_NAME,
    ),
    UpdaterStoreList(
        Store.DIRECT_URL,
        "Direct Download 2",
        R.drawable.appupdater_ic_cloud,
        CUSTOM_URL,
        SAMPLE_PACKAGE_NAME,
    ),
    // stores
    UpdaterStoreList(
        Store.GOOGLE_PLAY,
        "Google Play",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_google_play,
    ),
    UpdaterStoreList(
        Store.CAFE_BAZAAR,
        "Cafe Bazaar",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_bazar,
    ),
    UpdaterStoreList(
        Store.MYKET,
        "Myket",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_myket,
    ),
    UpdaterStoreList(
        Store.HUAWEI_APP_GALLERY,
        "App Gallery",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_app_gallery,
    ),
    UpdaterStoreList(
        Store.SAMSUNG_GALAXY_STORE,
        "Galaxy Store",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_galaxy_store,
    ),
    UpdaterStoreList(
        Store.AMAZON_APP_STORE,
        "Amazon App Store",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_amazon_app_store,
    ),
    UpdaterStoreList(
        Store.APTOIDE,
        "Aptoide",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_aptoide,
    ),
    UpdaterStoreList(
        Store.FDROID,
        "FDroid",
        packageName = FDROID_SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_fdroid,
    ),
    UpdaterStoreList(
        Store.MI_GET_APP_STORE,
        "Mi GetApp",
        packageName = GET_APP_SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_get_app_store,
    ),
    UpdaterStoreList(
        Store.ONE_STORE_APP_MARKET,
        "One Store",
        packageName = ONE_STORE_SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_one_store,
    ),
    UpdaterStoreList(
        Store.OPPO_APP_MARKET,
        "Oppo App Market",
        packageName = SAMPLE_PACKAGE_NAME,
        icon = R.drawable.appupdater_ic_oppo_app_market,
    ),
)
