package com.pouyaheydari.appupdater.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.R
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.databinding.DownloadStoresItemBinding

/**
 * Adapter to show stores on dialog page
 */
internal class StoresRecyclerAdapter(
    private val list: List<UpdaterStoreList>,
    private val theme: Theme,
    private val typeface: Typeface?,
    private val listener: (UpdaterStoreList) -> Unit,
) : RecyclerView.Adapter<StoresRecyclerAdapter.SoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DownloadStoresItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run { SoresViewHolder(this) }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) = holder.onBind(list[position])

    /**
     * ViewHolder for stores adapter
     */
    inner class SoresViewHolder(private val binding: DownloadStoresItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds data to view
         */
        fun onBind(item: UpdaterStoreList) {
            val txtStoreTitle = binding.txtStoreTitle
            val imgStore = binding.imgStore
            val textColor = when (theme) {
                Theme.LIGHT -> R.color.appupdater_text_colors
                Theme.DARK -> R.color.appupdater_text_colors_dark
            }
            txtStoreTitle.setTextColor(ContextCompat.getColor(binding.root.context, textColor))
            txtStoreTitle.text = item.title
            typeface?.let { txtStoreTitle.typeface = it }
            imgStore.setImageResource(item.icon)
            binding.root.setOnClickListener { listener(item) }
        }
    }
}
