package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InstallAPKIntentForPAndAboveTest {

    @Test
    fun testGetIntent() {
        val fileUri:Uri = mock()
        val installAPKIntent = InstallAPKIntentForPAndAbove()
        val intent = installAPKIntent.getIntent(fileUri)

        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(fileUri, intent.data)
        assertEquals(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK, intent.flags)
    }
}
