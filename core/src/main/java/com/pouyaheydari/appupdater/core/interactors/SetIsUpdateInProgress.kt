package com.pouyaheydari.appupdater.core.interactors

import com.pouyaheydari.appupdater.core.data.Repository

internal class SetIsUpdateInProgress {

    operator fun invoke(isUpdateInProgress: Boolean) {
        Repository.shouldShowUpdateInProgressDialog.tryEmit(isUpdateInProgress)
    }
}
