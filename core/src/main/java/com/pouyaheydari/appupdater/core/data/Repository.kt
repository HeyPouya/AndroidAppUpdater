package com.pouyaheydari.appupdater.core.data

import kotlinx.coroutines.flow.MutableSharedFlow

object Repository {

    var requestId = -10L

    val shouldShowUpdateInProgressDialog = MutableSharedFlow<Boolean>(replay = 1)
}
