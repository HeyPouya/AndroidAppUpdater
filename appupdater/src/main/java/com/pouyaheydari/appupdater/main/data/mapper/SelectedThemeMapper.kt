package com.pouyaheydari.appupdater.main.data.mapper

import android.content.Context
import android.content.res.Configuration
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.core.model.Theme.DARK
import com.pouyaheydari.appupdater.core.model.Theme.LIGHT
import com.pouyaheydari.appupdater.core.model.Theme.SYSTEM_DEFAULT
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme

internal fun mapToSelectedTheme(theme: Theme, context: Context): UserSelectedTheme = when (theme) {
    LIGHT -> UserSelectedTheme.LIGHT
    DARK -> UserSelectedTheme.DARK
    SYSTEM_DEFAULT -> if (context.isSystemInDarkMode()) UserSelectedTheme.DARK else UserSelectedTheme.LIGHT
}

private fun Context.isSystemInDarkMode(): Boolean =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
