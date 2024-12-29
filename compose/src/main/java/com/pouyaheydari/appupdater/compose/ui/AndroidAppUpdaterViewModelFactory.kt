package com.pouyaheydari.appupdater.compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaheydari.appupdater.compose.data.mapper.UpdaterViewModelDataMapper
import com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.GetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase

internal class AndroidAppUpdaterViewModelFactory(
    private val dialogData: UpdaterDialogData,
    private val getDownloadStateUseCase: GetDownloadStateUseCase = GetDownloadStateUseCase(UpdateInProgressRepositoryImpl),
    private val setDownloadStateUseCase: SetDownloadStateUseCase = SetDownloadStateUseCase(UpdateInProgressRepositoryImpl)
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelData = UpdaterViewModelDataMapper.map(dialogData)
        return AndroidAppUpdaterViewModel(viewModelData, getDownloadStateUseCase, setDownloadStateUseCase) as T
    }
}
