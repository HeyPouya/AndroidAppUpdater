package ir.heydarii.appupdater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList
import ir.heydarii.appupdater.utils.Utils
import kotlinx.android.synthetic.main.download_direct_item.view.*
import kotlinx.android.synthetic.main.download_stores_item.view.*

class DirectRecyclerAdapter(private val list: List<UpdaterStoreList>, val listener: (UpdaterStoreList) -> Unit) :
    RecyclerView.Adapter<DirectRecyclerAdapter.SoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.download_direct_item, parent, false)
        return SoresViewHolder(view, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SoresViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class SoresViewHolder(val view: View, val listener: (UpdaterStoreList) -> Unit) : RecyclerView.ViewHolder(view) {
        fun onBind(item: UpdaterStoreList) {
            view.txtDirect.text = item.title
            if (Utils.typeface != null)
                view.txtDirect.typeface = Utils.typeface
            view.setOnClickListener { listener(item) }
        }

    }
}