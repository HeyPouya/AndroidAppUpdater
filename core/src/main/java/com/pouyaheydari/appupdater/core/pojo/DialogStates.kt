package com.pouyaheydari.appupdater.core.pojo

import com.pouyaheydari.appupdater.core.stores.Stores

sealed class DialogStates(val store: Stores? = null, val apkUrl: String? = null) {
    object ShowUpdateInProgress : DialogStates()
    object HideUpdateInProgress : DialogStates()
    data class OpenStore(private val storeItem: Stores?) : DialogStates(store = storeItem)
    data class DownloadApk(private val url: String) : DialogStates(apkUrl = url)
}
