package com.pouyaheydari.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.tf

/**
 * Adapter to show stores on dialog page
 */
class StoresRecyclerAdapter(
    private val list: List<UpdaterStoreList>,
    private val theme: Theme?,
    private val listener: (UpdaterStoreList) -> Unit
) : RecyclerView.Adapter<StoresRecyclerAdapter.SoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).inflate(R.layout.download_stores_item, parent, false)
            .run { SoresViewHolder(this) }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) = holder.onBind(list[position])

    /**
     * ViewHolder for stores adapter
     */
    inner class SoresViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Binds data to view
         */
        fun onBind(item: UpdaterStoreList) {
            val txtStoreTitle = view.findViewById<TextView>(R.id.txtStoreTitle)
            val imgStore = view.findViewById<ImageView>(R.id.imgStore)
            val textColor = when (theme ?: Theme.LIGHT) {
                Theme.LIGHT -> R.color.appupdater_text_colors
                Theme.DARK -> R.color.appupdater_text_colors_dark
            }
            txtStoreTitle.setTextColor(ContextCompat.getColor(view.context, textColor))
            txtStoreTitle.text = item.title
            if (tf != null)
                txtStoreTitle.typeface = tf
            imgStore.setImageResource(item.icon)
            view.setOnClickListener { listener(item) }
        }
    }
}
