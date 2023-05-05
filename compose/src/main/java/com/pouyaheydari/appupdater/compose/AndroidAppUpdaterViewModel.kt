package com.pouyaheydari.appupdater.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.core.interactors.GetIsUpdateInProgress
import com.pouyaheydari.appupdater.core.pojo.DialogStates
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AndroidAppUpdaterViewModel : ViewModel() {
    val shouldShowUpdateInProgress = MutableStateFlow<DialogStates>(DialogStates.HideUpdateInProgress)

    fun onListListener(item: StoreListItem) {
        when (item.store) {
            Store.DIRECT_URL -> {
                observeUpdateInProgressStatus()
                shouldShowUpdateInProgress.value = DialogStates.DownloadApk(item.url)
            }

            else -> {
                val store = item.store.provider?.newInstance()?.also { it.setStoreData(item) }
                shouldShowUpdateInProgress.value = DialogStates.OpenStore(store)
            }
        }
    }

    private fun observeUpdateInProgressStatus() {
        viewModelScope.launch {
            GetIsUpdateInProgress().invoke().collectLatest {
                shouldShowUpdateInProgress.value = if (it) DialogStates.ShowUpdateInProgress else DialogStates.HideUpdateInProgress
            }
        }
    }
}
