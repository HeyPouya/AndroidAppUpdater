package com.pouyaheydari.appupdater.core.pojo

import com.pouyaheydari.appupdater.core.stores.Stores

/**
 * Represents UI states of the update dialog
 */
sealed interface DialogStates {
    object ShowUpdateInProgress : DialogStates
    object HideUpdateInProgress : DialogStates
    data class OpenStore(val store: Stores?) : DialogStates
    data class DownloadApk(val apkUrl: String) : DialogStates
}
