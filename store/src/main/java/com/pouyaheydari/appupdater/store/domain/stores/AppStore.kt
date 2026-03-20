package com.pouyaheydari.appupdater.store.domain.stores

import android.content.Intent
import android.os.Parcelable

/**
 * Strategy interface for an app store.
 *
 * Each implementation represents a specific app store (e.g. Google Play, Cafe Bazaar)
 * and knows how to build the [Intent] to open an app's page in that store.
 *
 * Implementations must be [Parcelable] so they can be passed through Bundle arguments.
 */
interface AppStore : Parcelable {
    /** Returns the [Intent] that opens this app's page in the store. */
    fun getIntent(): Intent

    /** Returns the [AppStoreType] enum value identifying this store. */
    fun getType(): AppStoreType

    /** Returns a human-readable display name for this store (e.g. "Google Play"). */
    fun getUserReadableName(): String
}
