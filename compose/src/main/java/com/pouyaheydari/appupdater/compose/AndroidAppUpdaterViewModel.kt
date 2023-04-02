package com.pouyaheydari.appupdater.compose

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.core.interactors.GetIsUpdateInProgress
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.utils.getApk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AndroidAppUpdaterViewModel : ViewModel() {
    private var updateInProgressState = mutableStateOf(false)
    val shouldShowUpdateInProgress: State<Boolean> = updateInProgressState

    fun onListListener(item: StoreListItem, activity: Activity?) {
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
}
