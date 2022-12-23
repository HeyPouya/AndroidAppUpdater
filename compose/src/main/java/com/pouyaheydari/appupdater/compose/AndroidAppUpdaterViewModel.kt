package com.pouyaheydari.appupdater.compose

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.getApk

class AndroidAppUpdaterViewModel : ViewModel() {
    private var updateInProgressState = mutableStateOf(false)
    var state: State<Boolean> = updateInProgressState

    fun onListListener(item: UpdaterStoreList, activity: Activity?) {
        when (item.store) {
            Store.DIRECT_URL -> getApk(item.url, activity) { shouldShowUpdateInProgress ->
                updateInProgressState.value = shouldShowUpdateInProgress
            }
            else -> item.store.provider?.newInstance()?.setStoreData(activity, item)
        }
    }
}
