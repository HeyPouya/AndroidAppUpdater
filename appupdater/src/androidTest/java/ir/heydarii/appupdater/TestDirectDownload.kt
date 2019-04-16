package ir.heydarii.appupdater

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import ir.heydarii.appupdater.directlink.DirectLinkDownload
import ir.heydarii.appupdater.exception.PermissionNotGrantedException
import ir.heydarii.appupdater.utils.Utils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestDirectDownload {
    lateinit var context: Context
    lateinit var directDownload: DirectLinkDownload
    lateinit var activity: Activity

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        directDownload = DirectLinkDownload()
    }

    @Test(expected = PermissionNotGrantedException::class)
    fun testGetApkPermissionNotProvided() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (!Utils.isPermissionGranted(permission, context))
            directDownload.getApk("http://heydarii.ir", context, null)
    }

}