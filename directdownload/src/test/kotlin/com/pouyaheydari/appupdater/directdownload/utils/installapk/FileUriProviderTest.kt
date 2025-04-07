package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.io.File

class FileUriProviderTest {

    private lateinit var fileUriProvider: FileUriProvider
    private lateinit var mockContext: Context
    private lateinit var mockApk: File
    private lateinit var mockUri: Uri

    @Before
    fun setup() {
        fileUriProvider = FileUriProvider()
        mockContext = mock(Context::class.java)
        mockApk = mock(File::class.java)
        mockUri = mock(Uri::class.java)
    }

    @Test
    fun `getFileUri returns file Uri for Android M`() {
        // Arrange
        Mockito.mockStatic(Uri::class.java).use { mockedUri ->
            whenever(Uri.fromFile(mockApk)).thenReturn(mockUri)

            // Act
            val result = fileUriProvider.getFileUri(mockContext, mockApk, Build.VERSION_CODES.M)

            // Assert
            assertEquals(mockUri, result)
            mockedUri.verify { Uri.fromFile(mockApk) }
        }
    }

    @Test
    fun `getFileUri returns file Uri from FileProvider for Android N and above`() {
        // Arrange
        val packageName = "com.pouyaheydari.appupdater"
        whenever(mockContext.packageName).thenReturn(packageName)

        Mockito.mockStatic(FileProvider::class.java).use { mockedFileProvider ->
            whenever(FileProvider.getUriForFile(mockContext, "$packageName.fileprovider", mockApk))
                .thenReturn(mockUri)

            // Act
            val result = fileUriProvider.getFileUri(mockContext, mockApk, Build.VERSION_CODES.N)

            // Assert
            assertEquals(mockUri, result)
            mockedFileProvider.verify {
                FileProvider.getUriForFile(mockContext, "$packageName.fileprovider", mockApk)
            }
        }
    }
}
