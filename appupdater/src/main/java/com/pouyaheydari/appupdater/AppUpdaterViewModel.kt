package com.pouyaheydari.appupdater

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.core.interactors.GetIsUpdateInProgress
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.utils.getApk
import com.pouyaheydari.appupdater.utils.TypefaceHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppUpdaterViewModel : ViewModel() {

    val updateInProgressState = MutableStateFlow(false)

    fun onListListener(item: StoreListItem, activity: Activity) {
        when (item.store) {
            Store.DIRECT_URL -> {
                observeUpdateInProgressStatus()
                getApk(item.url, activity)
            }
            else -> item.store.provider?.newInstance()?.setStoreData(activity, item)
        }
    }

    private fun observeUpdateInProgressStatus() {
        viewModelScope.launch {
            GetIsUpdateInProgress().invoke().collectLatest {
                updateInProgressState.value = it
            }
        }
    }

    override fun onCleared() {
        TypefaceHolder.clear()
        super.onCleared()
    }
}
