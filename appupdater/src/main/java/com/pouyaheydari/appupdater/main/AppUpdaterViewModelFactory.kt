package com.pouyaheydari.appupdater.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaheydari.appupdater.directdownload.domain.GetIsUpdateInProgressUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetUpdateInProgressUseCase

internal class AppUpdaterViewModelFactory(
    private val getIsUpdateInProgressUseCase: GetIsUpdateInProgressUseCase = GetIsUpdateInProgressUseCase(),
    private val setUpdateInProgressUseCase: SetUpdateInProgressUseCase = SetUpdateInProgressUseCase()
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppUpdaterViewModel(getIsUpdateInProgressUseCase, setUpdateInProgressUseCase) as T
    }
}
