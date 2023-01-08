package com.pouyaheydari.appupdater.core.interactors

import com.pouyaheydari.appupdater.core.data.Repository

internal class SetRequestIdInteractor {

    operator fun invoke(requestId: Long) {
        Repository.requestId = requestId
    }
}
