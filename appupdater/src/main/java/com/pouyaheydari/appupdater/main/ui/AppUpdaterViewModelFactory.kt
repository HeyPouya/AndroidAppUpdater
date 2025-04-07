package com.pouyaheydari.appupdater.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.GetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase

internal class AppUpdaterViewModelFactory(
    private val getDownloadStateUseCase: GetDownloadStateUseCase = GetDownloadStateUseCase(UpdateInProgressRepositoryImpl),
    private val setDownloadStateUseCase: SetDownloadStateUseCase = SetDownloadStateUseCase(UpdateInProgressRepositoryImpl)
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppUpdaterViewModel(getDownloadStateUseCase, setDownloadStateUseCase) as T
    }
}
