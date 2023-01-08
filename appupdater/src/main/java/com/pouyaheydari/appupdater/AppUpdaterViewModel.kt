package com.pouyaheydari.appupdater

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.utils.getApk
import com.pouyaheydari.appupdater.utils.TypefaceHolder
import kotlinx.coroutines.flow.MutableStateFlow

class AppUpdaterViewModel : ViewModel() {

    val updateInProgressState = MutableStateFlow(false)

    fun onListListener(item: StoreListItem, activity: Activity) {
        when (item.store) {
            Store.DIRECT_URL -> getApk(item.url, activity) {
                updateInProgressState.value = it
            }
            else -> item.store.provider?.newInstance()?.setStoreData(activity, item)
        }
    }

    override fun onCleared() {
        TypefaceHolder.clear()
        super.onCleared()
    }
}
