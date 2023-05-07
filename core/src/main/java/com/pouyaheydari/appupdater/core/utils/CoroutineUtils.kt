package com.pouyaheydari.appupdater.core.utils

import kotlinx.coroutines.delay

suspend fun runWithDelay(job: () -> Unit) {
    delay(100)
    job()
}
