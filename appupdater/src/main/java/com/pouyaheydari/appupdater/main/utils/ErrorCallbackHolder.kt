package com.pouyaheydari.appupdater.main.utils

/**
 * Holds an error callback lambda to be invoked when opening a store fails.
 */
internal object ErrorCallbackHolder {
    var callback: ((String) -> Unit)? = null

    fun clear() {
        callback = null
    }
}
