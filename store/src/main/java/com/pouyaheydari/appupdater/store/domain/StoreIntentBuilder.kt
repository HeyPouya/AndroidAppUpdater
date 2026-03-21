package com.pouyaheydari.appupdater.store.domain

import android.content.Intent
import android.net.Uri

/**
 * Internal builder for creating store-opening [Intent]s.
 */
internal object StoreIntentBuilder {
    /**
     * Builder for constructing an [Intent] to view a URI, optionally scoped to a specific store package.
     *
     * @param uriString the URI string to open (e.g. a market:// or https:// URL)
     */
    internal class Builder(
        private val uriString: String,
    ) {
        private var storePackageName: String? = null

        /**
         * Restricts the intent to the given store package.
         *
         * @param storePackageName the package name of the store app
         * @throws IllegalArgumentException if [storePackageName] is blank
         */
        fun withPackage(storePackageName: String): Builder {
            require(storePackageName.isNotBlank()) { "Store's package name must not be empty" }
            this.storePackageName = storePackageName
            return this
        }

        /** Builds and returns the configured [Intent]. */
        fun build(): Intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            storePackageName?.takeIf { it.isNotBlank() }?.let { setPackage(it) }
        }
    }
}
