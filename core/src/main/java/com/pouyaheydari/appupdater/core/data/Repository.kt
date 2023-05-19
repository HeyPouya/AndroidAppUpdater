package com.pouyaheydari.appupdater.core.data

import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Repository to store and publish the status of downloading the APK.
 */
internal object Repository {

    var requestId = -10L

    val shouldShowUpdateInProgressDialog = MutableSharedFlow<Boolean>(replay = 1)
}
