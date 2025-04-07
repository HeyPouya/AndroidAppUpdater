package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
class InstallAPKIntentForNToOTest {

    @Test
    fun testGetIntent() {
        val fileUri: Uri = mock()
        val installAPKIntentForNToO = InstallAPKIntentForNToO()

        val intent = installAPKIntentForNToO.getIntent(fileUri)

        assertEquals(Intent.ACTION_INSTALL_PACKAGE, intent.action)
        assertEquals(fileUri, intent.data)
        assertEquals(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_ACTIVITY_NEW_TASK,
            intent.flags
        )
    }
}
