# AndroidAppUpdater - Store module consumer ProGuard rules
# These rules are bundled with the library and applied to consumer projects.

# Keep all public API classes (domain models, interfaces, enums)
-keep class com.pouyaheydari.appupdater.store.domain.** { *; }

# Keep Parcelable CREATOR fields for all AppStore implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
