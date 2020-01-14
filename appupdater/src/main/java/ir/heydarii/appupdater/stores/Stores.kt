package ir.heydarii.appupdater.stores

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * this super class has some functions to use them in child classes
 */
abstract class Stores {

    /**
     * Sets intent of the store
     */
    abstract fun setStoreData(context: Context?, item: UpdaterStoreList)

    protected fun showStore(
        context: Context?,
        intent: Intent,
        item: UpdaterStoreList,
        store: Store
    ) {
        try {
            context?.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showUrlOrErrorToast(context, item, store)
        }
    }

    private fun showUrlOrErrorToast(context: Context?, item: UpdaterStoreList, store: Store) {
        if (item.url.isNotEmpty())
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        else {
            val storeName = store.name.toLowerCase(Locale.ROOT).replace("_", " ")
            Toast.makeText(
                context, context?.getString(R.string.please_install, storeName),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}