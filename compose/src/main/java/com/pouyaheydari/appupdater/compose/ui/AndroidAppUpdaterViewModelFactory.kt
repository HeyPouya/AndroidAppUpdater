package com.pouyaheydari.appupdater.compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaheydari.appupdater.compose.data.mapper.UpdaterViewModelDataMapper
import com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.GetIsUpdateInProgressUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetUpdateInProgressUseCase

internal class AndroidAppUpdaterViewModelFactory(
    private val dialogData: UpdaterDialogData,
    private val getIsUpdateInProgressUseCase: GetIsUpdateInProgressUseCase = GetIsUpdateInProgressUseCase(UpdateInProgressRepositoryImpl),
    private val setUpdateInProgressUseCase: SetUpdateInProgressUseCase = SetUpdateInProgressUseCase(UpdateInProgressRepositoryImpl)
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelData = UpdaterViewModelDataMapper.map(dialogData)
        return AndroidAppUpdaterViewModel(viewModelData, getIsUpdateInProgressUseCase, setUpdateInProgressUseCase) as T
    }
}
