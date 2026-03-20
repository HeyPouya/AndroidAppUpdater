package com.pouyaheydari.appupdater.store.domain

import com.pouyaheydari.appupdater.store.domain.stores.AppStore

/**
 * Sealed interface representing the result of attempting to open an app store.
 *
 * @see [showAppInSelectedStore]
 */
sealed interface AppStoreCallback {
    /**
     * Indicates the store was opened successfully.
     *
     * @property store the [AppStore] that was opened
     */
    data class Success(
        val store: AppStore,
    ) : AppStoreCallback

    /**
     * Indicates the store could not be opened.
     *
     * @property store the [AppStore] that failed to open
     * @property exception the exception that caused the failure (e.g. [android.content.ActivityNotFoundException]
     *   when no activity can handle the store intent, or [IllegalStateException] when context is null)
     */
    data class Failure(
        val store: AppStore,
        val exception: Exception,
    ) : AppStoreCallback
}
