package com.pouyaheydari.appupdater.main.data.mapper

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SelectedThemeMapperTest {

    private val context: Context = mock()
    private val resources: Resources = mock()

    @Test
    fun `mapToSelectedTheme should return LIGHT when theme is LIGHT`() {
        val result = mapToSelectedTheme(Theme.LIGHT, context)
        assertEquals(UserSelectedTheme.LIGHT, result)
    }

    @Test
    fun `mapToSelectedTheme should return DARK when theme is DARK`() {
        val result = mapToSelectedTheme(Theme.DARK, context)
        assertEquals(UserSelectedTheme.DARK, result)
    }

    @Test
    fun `mapToSelectedTheme should return DARK when system is in dark mode`() {
        whenever(context.resources).thenReturn(resources)
        whenever(resources.configuration).thenReturn(Configuration().apply {
            uiMode = Configuration.UI_MODE_NIGHT_YES
        })

        val result = mapToSelectedTheme(Theme.SYSTEM_DEFAULT, context)
        assertEquals(UserSelectedTheme.DARK, result)
    }

    @Test
    fun `mapToSelectedTheme should return LIGHT when system is in light mode`() {
        whenever(context.resources).thenReturn(resources)
        whenever(resources.configuration).thenReturn(Configuration().apply {
            uiMode = Configuration.UI_MODE_NIGHT_NO
        })

        val result = mapToSelectedTheme(Theme.SYSTEM_DEFAULT, context)
        assertEquals(UserSelectedTheme.LIGHT, result)
    }
}
