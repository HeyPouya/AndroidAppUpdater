package com.pouyaheydari.appupdater.core.interactors

import com.pouyaheydari.appupdater.core.data.Repository

class SetRequestIdInteractor {

    operator fun invoke(requestId: Long) {
        Repository.requestId = requestId
    }
}
