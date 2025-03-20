package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.content.Context
import android.os.Environment
import com.pouyaheydari.appupdater.core.utils.APK_NAME
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class APKFileProviderImplTest {

    private val context: Context = mock()
    private lateinit var apkFileProvider: APKFileProviderImpl

    @Before
    fun setUp() {
        apkFileProvider = APKFileProviderImpl()
    }

    @Test
    fun `getFile returns correct file path`() {
        val mockExternalFilesDir = File("/mock/external/files/dir")
        whenever(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)).thenReturn(mockExternalFilesDir)

        val expectedFile = File("/mock/external/files/dir/$APK_NAME")
        val actualFile = apkFileProvider.getFile(context)

        assertEquals(expectedFile, actualFile)
    }
}
