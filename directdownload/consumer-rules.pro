# AndroidAppUpdater - DirectDownload module consumer ProGuard rules
# These rules are bundled with the library and applied to consumer projects.

# Keep public API data classes
-keep class com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem { *; }
-keep class com.pouyaheydari.appupdater.directdownload.domain.DownloadState { *; }
-keep class com.pouyaheydari.appupdater.directdownload.domain.DownloadState$* { *; }

# Keep Parcelable CREATOR fields
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Keep the BroadcastReceiver declared in the manifest
-keep class com.pouyaheydari.appupdater.directdownload.receiver.DownloadFinishedReceiver { *; }

# Keep the FileProvider subclass referenced in the manifest
-keep class com.pouyaheydari.appupdater.directdownload.utils.downloadapk.APKFileProviderImpl { *; }
