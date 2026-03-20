package com.pouyaheydari.appupdater.main.ui.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.main.databinding.DownloadDirectItemBinding

internal class DirectRecyclerAdapter(
    private val list: List<DirectDownloadListItem>,
    private val typeface: Typeface?,
    private val listener: (DirectDownloadListItem) -> Unit,
) : RecyclerView.Adapter<DirectRecyclerAdapter.DirectDownloadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectDownloadViewHolder =
        DownloadDirectItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .run { DirectDownloadViewHolder(this) }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DirectDownloadViewHolder, position: Int) = holder.onBind(list[position])

    inner class DirectDownloadViewHolder(
        private val binding: DownloadDirectItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: DirectDownloadListItem) {
            val txtDirect = binding.txtDirect
            txtDirect.text = item.title
            typeface?.let { txtDirect.typeface = it }
            binding.root.setOnClickListener { listener(item) }
        }
    }
}
