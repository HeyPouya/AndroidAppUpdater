package com.pouyaheydari.appupdater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.core.interactors.GetIsUpdateInProgress
import com.pouyaheydari.appupdater.core.pojo.DialogStates
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.utils.TypefaceHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppUpdaterViewModel : ViewModel() {

    val updateInProgressState = MutableStateFlow<DialogStates>(DialogStates.HideUpdateInProgress)

    fun onListListener(item: StoreListItem) {
        when (item.store) {
            Store.DIRECT_URL -> {
                observeUpdateInProgressStatus()
                updateInProgressState.value = DialogStates.DownloadApk(item.url)
            }

            else -> {
                val store = item.store.provider?.newInstance()?.also { it.setStoreData(item) }
                updateInProgressState.value = DialogStates.OpenStore(store)
            }
        }
    }

    private fun observeUpdateInProgressStatus() {
        viewModelScope.launch {
            GetIsUpdateInProgress().invoke().collectLatest {
                updateInProgressState.value = if (it) DialogStates.ShowUpdateInProgress else DialogStates.HideUpdateInProgress
            }
        }
    }

    override fun onCleared() {
        TypefaceHolder.clear()
        super.onCleared()
    }
}
