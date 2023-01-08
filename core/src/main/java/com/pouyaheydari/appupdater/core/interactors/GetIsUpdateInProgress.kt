package com.pouyaheydari.appupdater.core.interactors

import com.pouyaheydari.appupdater.core.data.Repository

class GetIsUpdateInProgress {

    operator fun invoke() = Repository.shouldShowUpdateInProgressDialog
}
