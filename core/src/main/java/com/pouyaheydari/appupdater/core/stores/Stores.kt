package com.pouyaheydari.appupdater.core.stores

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast
import com.pouyaheydari.appupdater.core.R
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import java.util.Locale

/**
 * This is the super class of all store (e.g. [GooglePlayStore])
 */
abstract class Stores {

    private var intent: Intent? = null
    private var item: StoreListItem? = null
    private var store: Store? = null

    /**
     * Sets intent of the store
     */
    abstract fun setStoreData(item: StoreListItem)

    protected fun setData(intent: Intent, item: StoreListItem, store: Store) {
        this.intent = intent
        this.store = store
        this.item = item
    }

    fun showStore(context: Context?) {
        try {
            intent?.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            openWebViewToShowUrl(context, item, store)
        }
    }

    private fun openWebViewToShowUrl(context: Context?, item: StoreListItem?, store: Store?) {
        if (item?.url.orEmpty().isNotEmpty()) {
            try {
                val webViewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item?.url)).run {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                }
                context?.startActivity(webViewIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                showErrorToast(context, store)
            }
        } else {
            showErrorToast(context, store)
        }
    }

    private fun showErrorToast(context: Context?, store: Store?) {
        val storeName = store?.name?.lowercase(Locale.ROOT)?.replace("_", " ")
        Toast.makeText(
            context,
            context?.getString(R.string.appupdater_please_install, storeName),
            Toast.LENGTH_LONG,
        ).show()
    }
}
