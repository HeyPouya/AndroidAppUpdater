<p align="center">
<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/icon.png" width="250">
</p>

# Android App Updater

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Easy%20App%20Updater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7388)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7e8f094fd77044b5b26bc6c157bfbbc3)](https://app.codacy.com/gh/SirLordPouya/AndroidAppUpdater/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

App Updater is an easy-to-use and fully customizable library to show update dialog to users.
The update dialog can contain different app stores or direct download links. It can be used as a **FragmentDialog** or **Composable**.

<p align="center">
<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/header.png" width="800">
</p>

## Usage

### Available stores

The library currently supports bellow markets:

| App Store Name                                                          | App Store Enum             |
|-------------------------------------------------------------------------|----------------------------|
| [Google Play](https://play.google.com)                                  | Store.GOOGLE_PLAY          |
| [Huawei App Gallery](https://appgallery.huawei.com/)                    | Store.HUAWEI_APP_GALLERY   |
| [Samsung Galaxy Store](https://www.samsung.com/de/apps/galaxy-store/)   | Store.SAMSUNG_GALAXY_STORE |
| [Amazon App Store](https://www.amazon.com/gp/mas/get/amazonapp)         | Store.AMAZON_APP_STORE     |
| [Xiaomi GetApp Market](https://global.app.mi.com/)                      | Store.MI_GET_APP_STORE     |
| [Oppo App Market](https://oppomobile.com/)                              | Store.OPPO_APP_MARKET      |
| [F-Droid App Store](https://f-droid.org/)                               | Store.FDROID               |
| [Aptoide App Store](https://en.aptoide.com/)                            | Store.APTOIDE              |
| [OneStore App Market](https://m.onestore.co.kr/mobilepoc/main/main.omp) | Store.ONE_STORE_APP_MARKET |
| [Vivo V-AppStore](https://developer.vivo.com/home)                      | Store.V_APP_STORE          |
| [9-Apps Market](https://www.9apps.com/)                                 | Store.NINE_APPS_STORE      |
| [ZTE App Center](https://apps.ztems.com/)                               | Store.ZTE_APP_CENTER       |
| [Lenovo App Center](https://www.lenovomm.com)                           | Store.LENOVO_APP_CENTER    |
| [Tencent App Store](https://appstore.tencent.com/)                      | Store.TENCENT_APPS_STORE   |
| [Cafe Bazaar Store](https://cafebazaar.ir)                              | Store.CAFE_BAZAAR          |
| [Myket App Store](https://myket.ir/)                                    | Store.MYKET                |

### Showing stores

If you provide your application on above mentioned stores, you can list all of them in the update
dialog.
To create a new store:

```kotlin
val list = arrayListOf<StoreListItem>()
list.add(
    StoreListItem(
        store = Store.GOOGLE_PLAY,
        title = "Store Title",
        icon = R.drawable.appupdater_ic_google_play,
        url = "https://url/to/your/website",
        packageName = "YOUR_APPS_PACKAGE_NAME"
    )
)
```

Parameters of StoreListItem, in order:

| order | Parameter Name | Parameter Type | Description                                                                |
|-------|----------------|----------------|----------------------------------------------------------------------------|
| 1     | store          | Store          | Store Enum (e.g. GOOGLE_PLAY, CAFE_BAZAAR, ...)                            |
| 2     | title          | String         | Title of the store that user sees                                          |
| 3     | icon           | Int            | Icon of the store that user sees                                           |
| 4     | url            | String         | An url to open in a webview if the store is not installed in user's device |
| 5     | packageName    | String         | Package name of the application on the store                               |

***You can omit adding some properties in Kotlin:***

```kotlin
list.add(
    StoreListItem(
        store = Store.GOOGLE_PLAY,
        title = "Google Play",
        packageName = "YOUR_APPS_PACKAGE_NAME"
    )
)
```

### Direct Download

You can also make as many direct APK download links as you need. Users can download the APK directly
on their phone.
After downloading finishes, the install page will be shown to the user automatically.

```kotlin
list.add(
    StoreListItem(
        store = Store.DIRECT_URL,
        title = "Direct Download",
        url = "https://cafebazaar.ir/download/bazaar.apk"
    )
)
```

***Remember to put WRITE_EXTERNAL_STORAGE, INTERNET and REQUEST_INSTALL_PACKAGES permissions in your
manifest. The library asks for these permissions at runtime if needed***

## Show UpdateDialog with Jetpack Compose

You can show a native compose UpdateDialog to the user:

```kotlin
AndroidAppUpdaterTheme {
    AndroidAppUpdater(
        UpdaterDialogData(
            dialogTitle = "New Update !",
            dialogDescription = "Lots of new features! Update right now",
            storeList = list,
            theme = Theme.DARK
        )
    )
}
```

Parameters in the UpdaterDialogData in order are:

| order | Parameter Name     | Parameter Type         | Description                                                                                        |
|-------|--------------------|------------------------|----------------------------------------------------------------------------------------------------|
| 1     | dialogTitle        | String                 | Title of the update dialog                                                                         |
| 2     | dialogDescription  | String                 | Description of the update dialog                                                                   |
| 3     | storeList          | List<UpdaterStoreList> | List of Stores and Direct links to be shown to the user in the update dialog                       |                    |                        |                                                                  |
| 4     | onDismissRequested | () -> Unit             | Gets invoked when the user requests to dismiss the dialog                                          |
| 5     | typeface           | Typeface?              | Typeface to customize the font style if needed (You can omit this parameter if you don 't need it) |                    |                        |                                                                              |
| 6     | theme              | Theme                  | Theme of the dialog (can be set to Theme.Light, Theme.Dark or Theme.SYSTEM_DEFAULT)                |

### Show UpdateDialog with Fragments

```kotlin
AppUpdaterDialog.getInstance(
    UpdaterDialogData(
        title = "New Update !",
        description = "Lots of new features! Update right now",
        storeList = list,
        isForceUpdate = false,
        typeface = typeface,
        theme = Theme.LIGHT
    )
).show(supportFragmentManager, TAG)
```

Parameters in the UpdaterDialogData in order are:

| order | Parameter Name | Parameter Type         | Description                                                                                       |
|-------|----------------|------------------------|---------------------------------------------------------------------------------------------------|
| 1     | title          | String                 | Title of the update dialog                                                                        |
| 2     | description    | String                 | Description of the update dialog                                                                  |
| 3     | storeList      | List<UpdaterStoreList> | List of Stores and Direct links to be shown to the user in the update dialog                      |
| 4     | isForceUpdate  | Boolean                | Makes the dialog non-cancelable if sets to true                                                   |
| 5     | typeface       | Typeface?              | Typeface to customize the font style if needed (You can omit this parameter if you don't need it) |
| 6     | theme          | Theme                  | Theme of the dialog (can be set to Theme.Light, Theme.Dark or Theme.SYSTEM_DEFAULT)               |

### Customizing dialog texts

If you need to customize any texts in the updater or the update in progress dialogs, you can add
these strings resources in your strings.xml file to override them:

```xml

<resources>
    <string name="appupdater_please_wait">Please wait</string>
    <string name="appupdater_downloading_new_version">Downloading new version...</string>
    <string name="appupdater_download_notification_title">Downloading...</string>
    <string name="appupdater_download_notification_description">Downloading new version</string>
    <string name="appupdater_please_install">Please install</string>
    <string name="appupdater_or">or</string>
    <string name="appupdater_download_from_store">Download from store</string>
</resources>
```

### Default icons

There are default icons of all stores included in the library. You can use them or use your own icons.
Here is the list of icon names for each store:

| Market Name          | Icon name                                  |
|----------------------|--------------------------------------------|
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
| ZTE App Center       | R.drawable.appupdater_ic_zte_app_center    |
| Lenovo App Center    | R.drawable.appupdater_ic_lenovo_app_center |
| Tencent App Store    | R.drawable.appupdater_ic_tencent_app_store |
| Cafe Bazaar Store    | R.drawable.appupdater_ic_bazar             |
| Myket App Store      | R.drawable.appupdater_ic_myket             |

***Note: Don't forget to add import for drawables <import com.pouyaheydari.appupdater.R.\*>***

## Using library in Kotlin DSL style

This library also supports DSL.

### Adding Stores in DSL

```kotlin
val list = listOf(
    store {
        store = Store.GOOGLE_PLAY
        title = "Store Title"
        icon = R.drawable.appupdater_ic_google_play
        url = "https://url/to/your/website"
        packageName = "YOUR_APPS_PACKAGE_NAME"
    })
```

### Showing UpdateDialog in DSL

```kotlin
 updateDialogBuilder {
    title = "New Update !"
    description = "Lots of new features! Update right now"
    isForceUpdate = false
    typeface = Typeface.createFromAsset(assets, FONT_PATH)
    theme = Theme.DARK
    list = list
}.show(supportFragmentManager, TAG)
```

**Check the demo application to see it in your IDE.**

## Download

### Adding the dependency

Add this to your root **build.gradle** file:

```kotlin
allprojects {
    repositories {
        maven("https://jitpack.io")
    }
}
```

Then add these dependencies to your app build.gradle file:

```kotlin

// To use the library in Kotlin, Kotlin DSL or Java
implementation("com.github.SirLordPouya.AndroidAppUpdater:main:latest_version")

//To use the library with Jetpack Compose
implementation("com.github.SirLordPouya.AndroidAppUpdater:compose:latest_version")
```

## License

```text
Android App Updater is released under the Apache License 2.0. See LICENSE for details.
Copyright (c) 2018 Pouya Heydari
```

#### <div>Library's icon and style is designed by <a href="https://dribbble.com/Amirgk" title="Amir Gerdakane">Amir Gerdakane</a>
