package ir.heydarii.appupdater


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterFragmentModel
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList
import kotlinx.android.synthetic.main.fragment_app_updater_dialog.*
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ir.heydarii.appupdater.stores.CafeBazaarStore
import ir.heydarii.appupdater.stores.GooglePlayStore
import ir.heydarii.appupdater.stores.IranAppsStore
import ir.heydarii.appupdater.stores.MyketStore
import java.lang.Exception


//consts to use in this dialog
const val TITLE = "TITLE"
const val DATA_LIST = "DATA_LIST"

class AppUpdaterDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //setting isCancelable
        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val cancelableMode = data?.isForceUpdate
        setDialogCancelable(cancelableMode)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_updater_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getData that user set's via constructor
        getData()

    }

    /**
     * User sets this data via constructor
     */
    private fun getData() {
        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val title = data?.title
        val description = data?.description
        val list = data?.list
        setUpProperties(title, description, list)
    }

    /**
     * set's isCancelable functionality
     */
    private fun setDialogCancelable(cancelableMode: Boolean?) {
        cancelableMode?.let { isCancelable = it }
    }

    /**
     * sets title , description and stores list
     */
    private fun setUpProperties(title: String?, description: String?, list: List<UpdaterStoreList>?) {
        txtTitle.text = title
        txtDescription.text = description
        recycler.adapter = StoresRecyclerAdapter(list.orEmpty()) { onListListener(it) }
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    /**
     * listener to react to user, when user clicks on a store
     */
    private fun onListListener(item: UpdaterStoreList) {
        when (item.store) {
            Store.DIRECT_URL -> {
                //TODO : download app
            }
            Store.GOOGLE_PLAY -> {
                GooglePlayStore().setStoreData(context, item)
            }
            Store.CAFE_BAZAAR -> {
                CafeBazaarStore().setStoreData(context, item)
            }
            Store.MYKET -> {
                MyketStore().setStoreData(context, item)
            }
            Store.IRAN_APPS -> {
                IranAppsStore().setStoreData(context, item)
            }
        }
    }

    companion object {
        private val fragment = AppUpdaterDialog()

        fun getInstance(
            title: String = "",
            description: String = "",
            list: List<UpdaterStoreList>,
            isForce: Boolean = false
        ): AppUpdaterDialog {
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list, !isForce)
            bundle.putParcelable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
