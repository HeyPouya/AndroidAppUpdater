package ir.heydarii.appupdater

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ir.heydarii.appupdater.utils.PermissionUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Some sample tests
 */
@RunWith(AndroidJUnit4::class)
class TestUtils {
    lateinit var appContext: Context

    /**
     * Providing context
     */
    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().context
    }

    /**
     * Checks permission checker
     */
    @Test
    fun testCheckPermission() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionCheck = PermissionUtils().isPermissionGranted(permission, appContext)
        if (ContextCompat.checkSelfPermission(appContext, permission)
            == PackageManager.PERMISSION_GRANTED
        )
            Assert.assertTrue(
                "The permission is granted but the function is returning false",
                permissionCheck
            )
        else
            Assert.assertFalse(
                "The permission is not granted but the function is returning true",
                permissionCheck
            )
    }
}