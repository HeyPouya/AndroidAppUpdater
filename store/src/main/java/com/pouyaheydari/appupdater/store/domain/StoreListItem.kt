package com.pouyaheydari.appupdater.store.domain

import android.os.Parcelable
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import kotlinx.parcelize.Parcelize

/**
 * Represents a store item to be displayed in the updater dialog.
 *
 * @property store the [AppStore] implementation to open when the user taps this item
 * @property title the display title shown to the user
 * @property icon the drawable resource ID for the store icon
 */
@Parcelize
data class StoreListItem(
    val store: AppStore,
    val title: String = "",
    val icon: Int = 0,
) : Parcelable
