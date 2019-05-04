package ir.heydarii.appupdater


import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.directlink.DirectLinkDownload
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterFragmentModel
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList
import ir.heydarii.appupdater.stores.CafeBazaarStore
import ir.heydarii.appupdater.stores.GooglePlayStore
import ir.heydarii.appupdater.stores.IranAppsStore
import ir.heydarii.appupdater.stores.MyketStore
import ir.heydarii.appupdater.utils.Utils
import kotlinx.android.synthetic.main.fragment_app_updater_dialog.*


//consts to use in this dialog
const val DATA_LIST = "DATA_LIST"

class AppUpdaterDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //setting isCancelable
        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val cancelableMode = data?.isForceUpdate
        setDialogCancelable(cancelableMode)

        // Set transparent background and no title
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_updater_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()

        //make dialog's width matchParent
        dialog?.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
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


        //setting typefaces for text views
        if (Utils.typeface != null) {
            txtTitle.typeface = Utils.typeface
            txtDescription.typeface = Utils.typeface
            txtOr.typeface = Utils.typeface
            txtStore.typeface = Utils.typeface
        }

        val isStoreAndDirectAvailable = checkIfDirectAndStoreAvailable(list)
        hideOrLayoutIfNeeded(isStoreAndDirectAvailable)

        setUpBothRecyclers(list)
    }

    private fun setUpBothRecyclers(list: List<UpdaterStoreList>?) {
        val directLinks = ArrayList<UpdaterStoreList>()
        val storeLinks = ArrayList<UpdaterStoreList>()

        list?.forEach {
            if (it.store == Store.DIRECT_URL)
                directLinks.add(it)
            else
                storeLinks.add(it)
        }

        recyclerDirect.adapter = DirectRecyclerAdapter(directLinks.orEmpty()) { onListListener(it) }
        recyclerDirect.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerStores.adapter = StoresRecyclerAdapter(storeLinks.orEmpty()) { onListListener(it) }
        recyclerStores.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

    }

    /**
     * hide OrLayout if needed
     */
    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        if (storeAndDirectAvailable)
            linearLayout.visibility = View.VISIBLE
        else
            linearLayout.visibility = View.INVISIBLE
    }

    /**
     * check if there is no direct link or there is no stores,
     *
     */
    private fun checkIfDirectAndStoreAvailable(list: List<UpdaterStoreList>?): Boolean {
        var isDirectDownloadAvailable = false
        var isStoreDownloadAvailable = false

        //check if direct download is included in the list
        list?.forEach {
            if (it.store == Store.DIRECT_URL)
                isDirectDownloadAvailable = true
        }

        //check if the store download is included in the list
        list?.forEach {
            for (i in Store.values())
                if (it.store == i && i != Store.DIRECT_URL)
                    isStoreDownloadAvailable = true

        }

        return isDirectDownloadAvailable && isStoreDownloadAvailable

    }

    /**
     * listener to react to user, when user clicks on a store
     */
    private fun onListListener(item: UpdaterStoreList) {
        when (item.store) {
            Store.DIRECT_URL -> {
                DirectLinkDownload().getApk(item.url, activity, fragmentManager)
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

        // fragment variable to make this dialog singleton
        private val fragment = AppUpdaterDialog()

        /**
         * get Instance method
         */
        fun getInstance(title: String? = "", description: String? = "", list: List<UpdaterStoreList>, isForce: Boolean = false, typeface: Typeface? = null): AppUpdaterDialog {

            //set typeface in utils class to use later in application
            Utils.typeface = typeface

            // bundle to add data to our dialog
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list, !isForce)
            bundle.putParcelable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
