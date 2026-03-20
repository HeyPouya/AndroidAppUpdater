package com.pouyaheydari.appupdater.main.ui.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.main.R
import com.pouyaheydari.appupdater.main.databinding.DownloadStoresItemBinding
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme.DARK
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme.LIGHT
import com.pouyaheydari.appupdater.store.domain.StoreListItem

internal class StoresRecyclerAdapter(
    private val list: List<StoreListItem>,
    private val theme: UserSelectedTheme,
    private val typeface: Typeface?,
    private val listener: (StoreListItem) -> Unit,
) : RecyclerView.Adapter<StoresRecyclerAdapter.StoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DownloadStoresItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .run { StoresViewHolder(this) }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) = holder.onBind(list[position])

    inner class StoresViewHolder(
        private val binding: DownloadStoresItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: StoreListItem) {
            val txtStoreTitle = binding.txtStoreTitle
            val imgStore = binding.imgStore
            val textColor = when (theme) {
                LIGHT -> R.color.appupdater_text_colors
                DARK -> R.color.appupdater_text_colors_dark
            }
            txtStoreTitle.setTextColor(ContextCompat.getColor(binding.root.context, textColor))
            txtStoreTitle.text = item.title
            typeface?.let { txtStoreTitle.typeface = it }
            imgStore.setImageResource(item.icon)
            binding.root.setOnClickListener { listener(item) }
        }
    }
}
