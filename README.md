# Android App Updater [ specially for Iranian App Markets] 


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Easy%20App%20Updater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7388)
[![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Build Status](https://travis-ci.org/SirLordPouya/AndroidAppUpdater.svg?branch=master)](https://travis-ci.org/SirLordPouya/AndroidAppUpdater)

App Updater is a library to show update dialog to your users, whenever a new version of your application is available.
It is really easy-to-use and fully customizable.

##### It is fully integrated with Kotlin and androidX.


<img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_1.jpg" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_2.jpg" width="250"> <img src="https://raw.githubusercontent.com/SirLordPouya/AndroidAppUpdater/master/Screenshot_3.jpg" width="250">

## Releases:

#### Current release: [![](https://jitpack.io/v/SirLordPouya/AndroidAppUpdater.svg)](https://jitpack.io/#SirLordPouya/AndroidAppUpdater)


## Usage:

### Stores

you can show users as many stores as you need, to download your application from there. to make a new store :

```
 val list = ArrayList<UpdaterStoreList>()
        
 list.add(UpdaterStoreList(Store.DIRECT_URL, "Store Title", R.mipmap.ic_launcher , "https://url/app.apk", BuildConfig.APPLICATION_ID))
```

parameters of UpdaterStoreList, as the order you see in above line :

1. Store Type

2. Store Title That user Sees

3. Icon of Store that user sees

4. An url to show the user if the store is not installed in user's device

5. Package name of your application


##### or you can ommit adding some properties in Kotlin.  like :

```
  list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "Download From Google Play", packageName = BuildConfig.APPLICATION_ID))
```

### Stores
this library currently supports only these markets :

[Google Play](https://play.google.com)

[CafeBazaar](https://cafebazaar.ir)

[IranApps](https://iranapps.ir)

[Myket](https://myket.ir/)

To Select an Store you should use :

```
 Store.GOOGLE_PLAY
 Store.CAFE_BAZAAR
 Store.MYKET
 Store.IRAN_APPS
```

### Direct Download

you can also make as many direct apk download links as you need.
Users can download that apk directly on their phone, and after downloading finishes, the install page will be shown to the user.

##### Remember to get WRITE_EXTERNAL_STORAGE premission in runtime

```
 list.add(UpdaterStoreList(Store.DIRECT_URL, "Direct Download",R.mipmap.ic_launcher , "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))
```
### To Show UpdateDialog

```
 AppUpdaterDialog.getInstance("New Update!!!!", "Lots of new features!! upgrade yo the new version.", list, true, font).show(supportFragmentManager, "TAG")
```
parameters as the order you see in above line :

1. Update dialog title

2. Update dialog desciption

3. List of stores you created in last step

4. Is it a force update? (should dialog be cancelable or not)

5. Typeface to customize font style


## Download

#### Adding the depencency

Add this to your root *build.gradle* file:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```groovy
 implementation 'com.github.SirLordPouya:AndroidAppUpdater:1.1.0'
```

## License

```
LoadingFragment is released under the Apache License 2.0. See LICENSE for details.

Copyright (c) 2018 Pouya Heydari

```
