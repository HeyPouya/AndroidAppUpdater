package com.pouyaheydari.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.tf

/**
 * Adapter to show Direct download links
 */
class DirectRecyclerAdapter(
    private val list: List<UpdaterStoreList>,
    private val listener: (UpdaterStoreList) -> Unit
) : RecyclerView.Adapter<DirectRecyclerAdapter.SoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoresViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.download_direct_item, parent, false)
            .run { SoresViewHolder(this) }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) = holder.onBind(list[position])

    /**
     * Direct download ViewHolder
     */
    inner class SoresViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Binds data to layout
         */
        fun onBind(item: UpdaterStoreList) {
            val txtDirect = view.findViewById<TextView>(R.id.txtDirect)
            txtDirect.text = item.title
            if (tf != null)
                txtDirect.typeface = tf
            view.setOnClickListener { listener(item) }
        }
    }
}
