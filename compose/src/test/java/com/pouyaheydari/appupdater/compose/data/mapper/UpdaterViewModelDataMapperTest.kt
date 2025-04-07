package com.pouyaheydari.appupdater.compose.data.mapper

import com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData
import com.pouyaheydari.appupdater.core.model.Theme
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdaterViewModelDataMapperTest {

    @Test
    fun `map should correctly map UpdaterDialogData to UpdaterViewModelData`() {
        // Given
        val dialogData = UpdaterDialogData(
            dialogTitle = "Title",
            dialogDescription = "Description",
            dividerText = "Divider",
            storeList = listOf(),
            directDownloadList = listOf(),
            onDismissRequested = {},
            theme = Theme.SYSTEM_DEFAULT
        )

        // When
        val viewModelData = UpdaterViewModelDataMapper.map(dialogData)

        // Then
        assertEquals(dialogData.dialogTitle, viewModelData.dialogTitle)
        assertEquals(dialogData.dialogDescription, viewModelData.dialogDescription)
        assertEquals(dialogData.dividerText, viewModelData.dividerText)
        assertEquals(dialogData.storeList, viewModelData.storeList)
        assertEquals(dialogData.directDownloadList, viewModelData.directDownloadList)
        assertEquals(dialogData.onDismissRequested, viewModelData.onDismissRequested)
        assertEquals(dialogData.theme, viewModelData.theme)
    }
}
