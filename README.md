# Android App Updater (+ Iranian Android markets)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Easy%20App%20Updater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7388)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7e8f094fd77044b5b26bc6c157bfbbc3)](https://www.codacy.com/manual/SirLordPouya/AndroidAppUpdater?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SirLordPouya/AndroidAppUpdater&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Build Status](https://travis-ci.org/SirLordPouya/AndroidAppUpdater.svg?branch=master)](https://travis-ci.org/SirLordPouya/AndroidAppUpdater)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

<p align="center">
<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/icon.png" width="250">
</p>

App Updater is a library to show update dialog to your users, whenever a new version of your application is available.
It is really easy-to-use and fully customizable.

##### It is built with Kotlin and androidX and also supports DSL

<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_1.png" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_2.png" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_3.png" width="250">

## Releases

#### Current release: [![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)

## Kotlin Usage

### Stores

you can show users as many stores as you need, to download your application from there. to make a new store:

```kotlin
val list = ArrayList<UpdaterStoreList>()
list.add(UpdaterStoreList(Store.DIRECT_URL, "Store Title", R.mipmap.ic_launcher , "https://url/app.apk", BuildConfig.APPLICATION_ID))
```

parameters of UpdaterStoreList, as the order you see in above line:

1.  Store type
2.  Store title that user sees
3.  Icon of store that user sees
4.  An url to show the user if the store is not installed in user's device
5.  Package name of your application

##### or you can omit adding some properties in Kotlin

```kotlin
list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "Download From Google Play", packageName = BuildConfig.APPLICATION_ID))
```

### Available stores
this library currently supports only these markets:

[Google Play](https://play.google.com)

[CafeBazaar](https://cafebazaar.ir)

[IranApps](https://iranapps.ir)

[Myket](https://myket.ir/)

To Select an Store you should use:

```kotlin
Store.GOOGLE_PLAY
Store.CAFE_BAZAAR
Store.MYKET
Store.IRAN_APPS
```

### Direct Download

you can also make as many direct APK download links as you need.
Users can download that APK directly on their phone, and after downloading finishes, the install page will be shown to the user.

##### Remember to put WRITE_EXTERNAL_STORAGE, INTERNET and REQUEST_INSTALL_PACKAGES permissions in your manifest. The library asks these permissions at runtime if needed.

```kotlin
list.add(UpdaterStoreList(Store.DIRECT_URL, "Direct Download",R.mipmap.ic_launcher , "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))
```

### To Show UpdateDialog

```kotlin
AppUpdaterDialog.getInstance("New Update!!!!", "Lots of new features!! upgrade yo the new version.", list, true, font).show(supportFragmentManager, "TAG")
```
parameters as the order you see in above line:

1.  Update dialog title
2.  Update dialog description
3.  List of stores you created in last step
4.  Is it a force update? (should dialog be cancelable or not)
5.  Typeface to customize font style

### To change library's texts

In strings file, add these lines:

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

### To use default icons

I have added default icons of Iranian stores in the app.
if you like to use them, you can find them like :

```
R.drawable.appupdater_ic_google_play
R.drawable.appupdater_ic_bazar
R.drawable.appupdater_ic_myket
R.drawable.appupdater_ic_iran_apps
```

## Kotlin DSL

### Adding Stores in DSL

you can show users as many stores as you need, to download your application from there. to make a new store:

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

You can see the demo application to learn more about the usage.
## Download

#### Adding the dependency

Add this to your root *build.gradle* file:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```groovy
implementation 'com.github.SirLordPouya:AndroidAppUpdater:latest_version'
```

## License

```
LoadingFragment is released under the Apache License 2.0. See LICENSE for details.
Copyright (c) 2018 Pouya Heydari
```
#### <div>Library's icon and style is designed by <a href="https://dribbble.com/Amirgk" title="Amir Gerdakane">Amir Gerdakane</a>
