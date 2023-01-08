package com.pouyaheydari.appupdater.core.interactors

import com.pouyaheydari.appupdater.core.data.Repository

class GetRequestIdInteractor {

    operator fun invoke(): Long = Repository.requestId
}
