package ir.heydarii.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import ir.heydarii.appupdater.utils.typeface

/**
 * Adapter to show Direct download links
 */
class DirectRecyclerAdapter(
    private val list: List<UpdaterStoreList>,
    private val listener: (UpdaterStoreList) -> Unit
) :
    RecyclerView.Adapter<DirectRecyclerAdapter.SoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoresViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.download_direct_item, parent, false)
        return SoresViewHolder(view, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    /**
     * Direct download ViewHolder
     */
    class SoresViewHolder(private val view: View, val listener: (UpdaterStoreList) -> Unit) :
        RecyclerView.ViewHolder(view) {

        /**
         * Binds data to layout
         */
        fun onBind(item: UpdaterStoreList) {
            val txtDirect = view.findViewById<TextView>(R.id.txtDirect)
            txtDirect.text = item.title
            if (typeface != null)
                txtDirect.typeface = typeface
            view.setOnClickListener { listener(item) }
        }
    }
}