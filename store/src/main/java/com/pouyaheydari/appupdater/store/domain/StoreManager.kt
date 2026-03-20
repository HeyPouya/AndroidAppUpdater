package com.pouyaheydari.appupdater.store.domain

import android.content.ActivityNotFoundException
import android.content.Context
import com.pouyaheydari.appupdater.store.domain.stores.AppStore

/**
 * Attempts to open the given [store] in the corresponding app store.
 *
 * @param context the Android context used to start the store activity
 * @param store the [AppStore] to open
 * @param callback invoked with [AppStoreCallback.Success] on success or [AppStoreCallback.Failure] on failure
 */
fun showAppInSelectedStore(context: Context?, store: AppStore, callback: (AppStoreCallback) -> Unit) {
    if (context == null) {
        callback(AppStoreCallback.Failure(store, IllegalStateException("Context is null, cannot open store")))
        return
    }
    try {
        val intent = store.getIntent()
        context.startActivity(intent)
        callback(AppStoreCallback.Success(store))
    } catch (exception: ActivityNotFoundException) {
        callback(AppStoreCallback.Failure(store, exception))
    }
}
