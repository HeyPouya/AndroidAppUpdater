package com.pouyaheydari.appupdater

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pouyaheydari.appupdater.core.utils.isPermissionGranted
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TestUtils {
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
        val permissionCheck = appContext.isPermissionGranted(permission)
        if (ContextCompat.checkSelfPermission(appContext, permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Assert.assertTrue(
                "The permission is granted but the function is returning false",
                permissionCheck,
            )
        } else {
            Assert.assertFalse(
                "The permission is not granted but the function is returning true",
                permissionCheck,
            )
        }
    }
}
