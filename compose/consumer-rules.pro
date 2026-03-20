# AndroidAppUpdater - Compose module consumer ProGuard rules
# These rules are bundled with the library and applied to consumer projects.

# Keep the public composable entry point (referenced by name in consumer code)
-keep class com.pouyaheydari.appupdater.compose.ui.AndroidAppUpdaterScreenKt { *; }

# Keep public API model classes
-keep class com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData { *; }

# Keep Parcelable CREATOR fields
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
