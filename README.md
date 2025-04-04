<p align="center">
  <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/icon.png" width="250">
</p>

# Android App Updater

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Easy%20App%20Updater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7388)[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7e8f094fd77044b5b26bc6c157bfbbc3)](https://app.codacy.com/gh/SirLordPouya/AndroidAppUpdater/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)[![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

**Android App Updater** is a powerful and flexible library to display an update dialog in your Android app. It supports multiple app stores and direct APK download links. Use it easily with **DialogFragment** or **Jetpack Compose**.

<p align="center">
  <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/pics/header.png" width="800">
</p>

## 🚀 Installation

### Step 1: Add JitPack Repository

Add the following to your project’s **build.gradle.kts**:

```kotlin
allprojects {
    repositories {
        maven("https://jitpack.io")
    }
}
```

Or to your project’s **settings.gradle.kts**:
```kotlin
dependencyResolutionManagement {
    repositories {
        maven ( url =  "https://jitpack.io" )
    }
}
```

### Step 2: Add Dependencies

```kotlin
// For DialogFragment integration
implementation("com.github.SirLordPouya.AndroidAppUpdater:main:latest_version")

// For Jetpack Compose integration
implementation("com.github.SirLordPouya.AndroidAppUpdater:compose:latest_version")
```

## 🎯 Supported App Stores

| Store                | Enum                              |
|----------------------|-----------------------------------|
| Google Play          | AppStoreType.GOOGLE_PLAY          |
| Huawei App Gallery   | AppStoreType.HUAWEI_APP_GALLERY   |
| Samsung Galaxy Store | AppStoreType.SAMSUNG_GALAXY_STORE |
| Amazon App Store     | AppStoreType.AMAZON_APP_STORE     |
| Xiaomi GetApp Market | AppStoreType.MI_GET_APP_STORE     |
| Oppo App Market      | AppStoreType.OPPO_APP_MARKET      |
| F-Droid              | AppStoreType.FDROID               |
| Aptoide              | AppStoreType.APTOIDE              |
| OneStore             | AppStoreType.ONE_STORE_APP_MARKET |
| Vivo V-AppStore      | AppStoreType.V_APP_STORE          |
| 9-Apps Market        | AppStoreType.NINE_APPS_STORE      |
| ZTE App Center       | AppStoreType.ZTE_APP_CENTER       |
| Lenovo App Center    | AppStoreType.LENOVO_APP_CENTER    |
| Tencent App Store    | AppStoreType.TENCENT_APPS_STORE   |
| Cafe Bazaar          | AppStoreType.CAFE_BAZAAR          |
| Myket                | AppStoreType.MYKET                |

## 📌 Usage

### Define App Stores

```kotlin
val storesList = listOf(
    StoreListItem(
        store = StoreFactory.getStore(AppStoreType.GOOGLE_PLAY, "YOUR_APP_PACKAGE"),
        title = "Google Play",
        icon = R.drawable.appupdater_ic_google_play
    )
)
```

### Default Store Icons

All supported store icons are available in the library. You can use them directly:

| Market Name          | Icon Resource                              |
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

👉 **Note**: Make sure to import:
```kotlin
import com.pouyaheydari.appupdater.R.*
```

### Add a Direct Download Link

Add the required permissions to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```

Then create the download link:

```kotlin
val directDownloadLinksList = listOf(
    DirectDownloadListItem(
        title = "Direct Download",
        url = "https://example.com/app.apk"
    )
)
```

### Show the Update Dialog

#### ✅ With Jetpack Compose

```kotlin
var shouldShowDialog by remember { mutableStateOf(true) }

if (shouldShowDialog) {
    AndroidAppUpdater(
        dialogData = UpdaterDialogData(
            dialogTitle = "New Update Available",
            dialogDescription = "We've fixed bugs and improved performance!",
            dividerText = "Or",
            storeList = storesList,
            directDownloadList = directDownloadLinksList,
            onDismissRequested = { shouldShowDialog = false },
            errorWhileOpeningStoreCallback = { storeName -> /* Handle error */ },
            theme = Theme.LIGHT
        )
    )
}
```

#### ✅ With Fragments

```kotlin
val data = UpdaterDialogData(
    title = "New Update Available",
    description = "We've fixed bugs and improved performance!",
    storeList = storesList,
    directDownloadList = directDownloadLinksList,
    isForceUpdate = false,
    errorWhileOpeningStoreCallback = { storeName -> /* Handle error */ },
    theme = Theme.SYSTEM_DEFAULT,
)

AppUpdaterDialog.getInstance(data).show(supportFragmentManager, "UPDATE_DIALOG")
```

## 🎨 Customization

You can override default texts in `strings.xml`:

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

## 📝 License

```
Android App Updater is released under the Apache License 2.0. See LICENSE for details.
Copyright (c) 2018 Pouya Heydari
```

<p align="center">Library icon and design by <a href="https://dribbble.com/Amirgk" title="Amir Gerdakane">Amir Gerdakane</a></p>
