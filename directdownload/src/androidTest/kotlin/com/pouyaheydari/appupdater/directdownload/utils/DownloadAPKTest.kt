package com.pouyaheydari.appupdater.directdownload.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pouyaheydari.appupdater.directdownload.utils.installapk.getIntent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.io.File

@RunWith(AndroidJUnit4::class)
class DownloadAPKTest {

    @get:Rule
    val intentsTestRule = IntentsRule()

    @Test
    fun test_fileDoesNotExist_InstallationDoesNotStart() {
        val file: File = mock()
        whenever(file.exists()).thenReturn(false)
        val context: Context = mock()

        getIntent(context, file, Build.VERSION_CODES.P)

        verifyNoInteractions(context)
    }

    @Test
    fun test_wheneverBuildVersionIs_M_Then_CorrectIntentIsSent() {
        val file: File = mock()
        whenever(file.exists()).thenReturn(true)
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        getIntent(context, file, Build.VERSION_CODES.M)

        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        Intents.intended(IntentMatchers.hasType(APK_MIME_TYPE))
        Intents.intended(IntentMatchers.hasFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

//    @Test
//    fun test_wheneverBuildVersionIs_M_Then_CorrectIntentIsSent() {
//        val file = File("file")
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//
//        installAPK(appContext, file, Build.VERSION_CODES.P)
//
//        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
//        Intents.intended(IntentMatchers.hasType(APK_MIME_TYPE))
//        Intents.intended(IntentMatchers.hasFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
//    }

}
