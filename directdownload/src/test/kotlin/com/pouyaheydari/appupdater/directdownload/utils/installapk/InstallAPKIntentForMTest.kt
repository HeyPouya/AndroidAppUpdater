package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InstallAPKIntentForMTest {

    @Test
    fun testGetIntent() {
        val fileUri: Uri = mock()
        val installAPKIntentForM = InstallAPKIntentForM()

        val intent = installAPKIntentForM.getIntent(fileUri)

        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(fileUri, intent.data)
        assertEquals(
            Intent.FLAG_ACTIVITY_NEW_TASK,
            intent.flags
        )
    }
}
