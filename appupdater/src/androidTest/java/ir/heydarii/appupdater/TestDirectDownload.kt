package ir.heydarii.appupdater

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ir.heydarii.appupdater.directlink.DirectLinkDownload
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestDirectDownload {
    lateinit var context: Context
    lateinit var directDownload: DirectLinkDownload

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        directDownload = DirectLinkDownload()
    }

    @Test
    fun testInstallApk() {
        directDownload.installApk(context)
    }
}