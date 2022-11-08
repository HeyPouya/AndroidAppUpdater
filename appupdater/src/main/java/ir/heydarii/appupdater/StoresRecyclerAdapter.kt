package ir.heydarii.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import ir.heydarii.appupdater.utils.typeface

/**
 * Adapter to show stores on dialog page
 */
class StoresRecyclerAdapter(
    private val list: List<UpdaterStoreList>,
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
            txtStoreTitle.text = item.title
            if (typeface != null)
                txtStoreTitle.typeface = typeface
            imgStore.setImageResource(item.icon)
            view.setOnClickListener { listener(item) }
        }
    }
}
