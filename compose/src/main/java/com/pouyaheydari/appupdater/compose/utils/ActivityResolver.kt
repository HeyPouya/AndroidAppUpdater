package com.pouyaheydari.appupdater.compose.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.platform.LocalContext

/**
 * Tries to return an [Activity] instance from the compose [LocalContext]
 *
 * @return Either an [Activity] instance or null
 */
internal fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
