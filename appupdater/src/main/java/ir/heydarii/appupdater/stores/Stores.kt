package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

/**
 * this super class has some functions to use them in child classes
 */
abstract class Stores {

   abstract  fun setStoreData(context: Context?,item: UpdaterStoreList)

   protected fun showStore(context: Context?, intent: Intent, item: UpdaterStoreList, store: Store) {
       try {
           context?.startActivity(intent)
       } catch (e: Exception) {
           showUrlOrException(context, item, store)
       }
   }

    private fun showUrlOrException(context: Context?, item: UpdaterStoreList, store: Store) {
        if (item.url.isNotEmpty())
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        else
            Toast.makeText(context, context?.getString(R.string.please_install) + store.name.toLowerCase(), Toast.LENGTH_LONG).show()
    }

}