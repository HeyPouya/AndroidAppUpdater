# AndroidAppUpdater - AppUpdater (XML/View) module consumer ProGuard rules
# These rules are bundled with the library and applied to consumer projects.

# Keep the public DialogFragment entry point
-keep class com.pouyaheydari.appupdater.main.ui.AppUpdaterDialog { *; }

# Keep public API model classes
-keep class com.pouyaheydari.appupdater.main.ui.model.UpdaterDialogData { *; }
-keep class com.pouyaheydari.appupdater.main.ui.model.UpdaterFragmentModel { *; }

# Keep DSL builder functions and classes
-keep class com.pouyaheydari.appupdater.main.dsl.** { *; }

# Keep Parcelable CREATOR fields
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
