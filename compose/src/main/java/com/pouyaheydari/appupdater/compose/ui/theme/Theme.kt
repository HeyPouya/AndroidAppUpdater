package com.pouyaheydari.appupdater.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColors(
    onSurface = White80,
    surface = Color.Black,
    background = White10,
)

private val LightColorScheme = lightColors(
    onSurface = Grey60,
    surface = Color.White,
    background = Black7,
)

@Composable
fun AndroidAppUpdaterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content,
    )
}
