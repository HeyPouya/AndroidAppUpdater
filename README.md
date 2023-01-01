# Android App Updater (+ Support for third party markets)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Easy%20App%20Updater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7388)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7e8f094fd77044b5b26bc6c157bfbbc3)](https://www.codacy.com/manual/SirLordPouya/AndroidAppUpdater?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SirLordPouya/AndroidAppUpdater&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Build Status](https://travis-ci.org/SirLordPouya/AndroidAppUpdater.svg?branch=master)](https://travis-ci.org/SirLordPouya/AndroidAppUpdater)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

<p align="center">
<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/icon.png" width="250">
</p>

App Updater is an  easy-to-use and fully customizable library to show update dialog to users.

**Supports Kotlin DSL and Compose**

<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/Screenshot_1.png" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/Screenshot_2.png" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/Screenshot_3.png" width="250">

## Kotlin Usage

### Stores

If you provide your application on stores (other than  Google Play), you can list all of them in the update dialog.
To create a new store:

```kotlin
val list = arrayListOf<UpdaterStoreList>()
list.add(
    UpdaterStoreList(
        store = Store.GOOGLE_PLAY,
        title = "Store Title",
        icon = R.mipmap.ic_launcher,
        url = "https://url/to/your/website",
        packageName = SAMPLE_PACKAGE_NAME)
)
```

parameters of UpdaterStoreList, as the order you see in above line:

1.  Store type (e.g. GOOGLE_PLAY, CAFE_BAZAAR, ...)
2.  Store title that user sees
3.  Icon of store that user sees
4.  An url to show the user if the store is not installed in user's device
5.  Package name of the application on the store

#### You can omit adding some properties in Kotlin

```kotlin
list.add(
    UpdaterStoreList(
      store = Store.GOOGLE_PLAY,
      title = "Google Play",
      packageName = BuildConfig.APPLICATION_ID)
)
```

### Available stores and icons

This library currently supports these markets:

* [Store.GOOGLE_PLAY](https://play.google.com)
* [Store.AMAZON_APP_STORE](https://www.amazon.com/gp/mas/get/amazonapp)
* [Store.HUAWEI_APP_GALLERY](https://appgallery.huawei.com/)
* [Store.SAMSUNG_GALAXY_STORE](https://www.samsung.com/de/apps/galaxy-store/)
* [Store.MI_GET_APP_STORE](https://global.app.mi.com/)
* [Store.OPPO_APP_MARKET](https://oppomobile.com/)
* [Store.FDROID](https://f-droid.org/)
* [Store.APTOIDE](https://en.aptoide.com/)
* [Store.ONE_STORE_APP_MARKET](https://m.onestore.co.kr/mobilepoc/main/main.omp)
* [Store.V_APP_STORE](https://developer.vivo.com/home)
* [Store.NINE_APPS_STORE](https://www.9apps.com/)
* [Store.TENCENT_APPS_STORE](https://appstore.tencent.com/)
* [Store.CAFE_BAZAAR](https://cafebazaar.ir)
* [Store.MYKET](https://myket.ir/)

### Direct Download

You can also make as many direct APK download links as you need.
Users can download that APK directly on their phone. After downloading finishes, the install page will be shown to the user automatically.

```kotlin
list.add(
    UpdaterStoreList(
      store = Store.DIRECT_URL,
      title = "Direct Download",
      icon = R.mipmap.ic_launcher,
      url = "https://cafebazaar.ir/download/bazaar.apk",
      packageName = BuildConfig.APPLICATION_ID)
)
```
***Remember to put WRITE_EXTERNAL_STORAGE, INTERNET and REQUEST_INSTALL_PACKAGES permissions in your manifest. The library asks these permissions at runtime if needed***

### To Show UpdateDialog

```kotlin
AppUpdaterDialog.getInstance(
    title =  getString(R.string.library_title),
    description = getString(R.string.library_description),
    storeList = list,
    isForce = false,
    typeface = font,
    theme = Theme.LIGHT
).show(supportFragmentManager, TAG)
```

Parameters in the order are:

1.  Update dialog title
2.  Update dialog description
3.  List of stores you created in last step
4.  Is it a force update? (should dialog be cancelable or not)
5.  Typeface to customize font style
6. Theme of the dialog (Theme.Light or Theme.Dark)

### To change library's texts

In strings file, add these lines and customize them according to your needs:

```xml
<resources>
<string name="please_wait">Please wait</string>
<string name="downloading_new_version">Downloading new version...</string>
<string name="download_notification_title">Downloading...</string>
<string name="download_notification_description">Downloading new version</string>
<string name="please_install">Please install</string>
<string name="or">or</string>
<string name="download_from_store">Download from store</string>
</resources>
```

### Using default icons

Some default icons are included in the library.

| Market Name          | Icon name                                  |
| -------------------- | ------------------------------------------ |
| Google Play          | R.drawable.appupdater_ic_google_play       |
| Huawei App Gallery   | R.drawable.appupdater_ic_app_gallery       |
| Samsung Galaxy Store | R.drawable.appupdater_ic_galaxy_store      |
| Amazon App Store     | R.drawable.appupdater_ic_amazon_app_store  |
| Xiaomi GetApp Store  | R.drawable.appupdater_ic_get_app_store     |
| Oppo App Market      | R.drawable.appupdater_ic_oppo_app_market   |
| F-Droid App Store    | R.drawable.appupdater_ic_fdroid            |
| Aptoide App Store    | R.drawable.appupdater_ic_aptoide           |
| OneStore App Market  | R.drawable.appupdater_ic_one_store         |
| Vivo V-AppStore      | R.drawable.appupdater_ic_v_app_store       |
| 9-Apps Market        | R.drawable.appupdater_ic_nine_apps         |
| Tencent App Store    | R.drawable.appupdater_ic_tencent_app_store |
| Cafe Bazaar Store    | R.drawable.appupdater_ic_bazar             |
| Myket App Store      | R.drawable.appupdater_ic_myket             |

## Jetpack Compose
You can also show a native compose UpdateDialog to the user:

```kotlin
AndroidAppUpdaterTheme {
    AndroidAppUpdater(
        dialogTitle = stringResource(id = R.string.appupdater_app_name),
        dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
        storeList = storeList,
        theme = Theme.DARK
    )
}
```

## Kotlin DSL
This library also supports DSL. To use it, add the required dependency first.

### Adding Stores in DSL

```kotlin
store {
       store = Store.DIRECT_URL
       title = "Direct Download 2"
       icon = R.mipmap.ic_launcher
       url = "https://cafebazaar.ir/download/bazaar.apk"
       packageName = BuildConfig.APPLICATION_ID
}
```

### Showing UpdateDialog in DSL

```kotlin
 updateDialogBuilder {
            title = "New Update !"
            description = "Lots of new features! Update right now"
            isForceUpdate = false
            typeface = Typeface.createFromAsset(assets, FONT_PATH)
            theme = Theme.DARK
            list = listOf(
                store {
                    store = Store.DIRECT_URL
                    title = "Direct Download 1"
                    icon = R.mipmap.ic_launcher
                    url = "https://cafebazaar.ir/download/bazaar.apk"
                    packageName = BuildConfig.APPLICATION_ID
                })
}.show(supportFragmentManager, TAG)
```
**Check the demo application to see it in your IDE.**

## Download

### Adding the dependency

Add this to your root **build.gradle** file:

```groovy
allprojects {
    repositories {
        maven ("https://jitpack.io")
    }
}
```

Now add the dependency to your app build.gradle file:

```groovy
// Always add the core dependency
implementation ("com.github.SirLordPouya.AndroidAppUpdater:core:latest_version")

// To use the library in Kotlin & Java
implementation ("com.github.SirLordPouya.AndroidAppUpdater:main:latest_version")

//To use the library with Kotlin DSL, Kotlin & Java
implementation ("com.github.SirLordPouya.AndroidAppUpdater:dsl:latest_version")

//To use the library with Jetpack Compose
implementation ("com.github.SirLordPouya.AndroidAppUpdater:compose:latest_version")
```

## License

```text
Android App Updater is released under the Apache License 2.0. See LICENSE for details.
Copyright (c) 2018 Pouya Heydari
```

### <div>Library's icon and style is designed by <a href="https://dribbble.com/Amirgk" title="Amir Gerdakane">Amir Gerdakane</a>
