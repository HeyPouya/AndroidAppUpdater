package ir.heydarii.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList
import ir.heydarii.appupdater.utils.Utils
import kotlinx.android.synthetic.main.download_stores_item.view.*

/**
 * Adapter to show stores on dialog page
 */
class StoresRecyclerAdapter(private val list: List<UpdaterStoreList>, private val listener: (UpdaterStoreList) -> Unit) :
    RecyclerView.Adapter<StoresRecyclerAdapter.SoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.download_stores_item, parent, false)
        return SoresViewHolder(view, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    /**
     * ViewHolder for stores adapter
     */
    class SoresViewHolder(private val view: View, val listener: (UpdaterStoreList) -> Unit) :
        RecyclerView.ViewHolder(view) {

        /**
         * Binds data to view
         */
        fun onBind(item: UpdaterStoreList) {
            view.txtStoreTitle.text = item.title
            if (Utils.typeface != null)
                view.txtStoreTitle.typeface = Utils.typeface
            view.imgStore.setImageResource(item.icon)
            view.setOnClickListener { listener(item) }
        }

    }
}