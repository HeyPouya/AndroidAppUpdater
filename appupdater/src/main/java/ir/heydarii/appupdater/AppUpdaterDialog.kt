package ir.heydarii.appupdater


import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import ir.heydarii.appupdater.directlink.DirectLinkDownload
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterFragmentModel
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import ir.heydarii.appupdater.stores.CafeBazaarStore
import ir.heydarii.appupdater.stores.GooglePlayStore
import ir.heydarii.appupdater.stores.IranAppsStore
import ir.heydarii.appupdater.stores.MyketStore
import ir.heydarii.appupdater.utils.Constants
import ir.heydarii.appupdater.utils.Constants.Companion.DATA_LIST
import kotlinx.android.synthetic.main.fragment_app_updater_dialog.*

/**
 * Shows ForceUpdate Dialog Fragment
 */
class AppUpdaterDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //setting isCancelable
        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val cancelableMode = data?.isForceUpdate
        setDialogCancelable(cancelableMode)

        // Set background for the dialog
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.dialog_background
            )
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_updater_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()

        //make dialog's width matchParent
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
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
        checkNotNull(list)
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
    private fun setUpProperties(
        title: String?,
        description: String?,
        list: List<UpdaterStoreList>
    ) {

        txtTitle.text = title
        txtDescription.text = description

        //setting typefaces for text views
        if (Constants.typeface != null) {
            txtTitle.typeface = Constants.typeface
            txtDescription.typeface = Constants.typeface
            txtOr.typeface = Constants.typeface
            txtStore.typeface = Constants.typeface
        }

        hideOrLayoutIfNeeded(checkIfDirectAndStoreAvailable(list))

        setUpBothRecyclers(list)
    }

    private fun setUpBothRecyclers(list: List<UpdaterStoreList>?) {
        val directLinks by lazy { ArrayList<UpdaterStoreList>() }
        val storeLinks by lazy { ArrayList<UpdaterStoreList>() }

        list?.forEach {
            if (it.store == Store.DIRECT_URL)
                directLinks.add(it)
            else
                storeLinks.add(it)
        }

        recyclerDirect.adapter = DirectRecyclerAdapter(directLinks) { onListListener(it) }

        recyclerStores.adapter = StoresRecyclerAdapter(storeLinks) { onListListener(it) }

    }

    /**
     * hide OrLayout if needed
     */
    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        if (storeAndDirectAvailable)
            linearLayout.visibility = View.VISIBLE
        else
            linearLayout.visibility = View.GONE
    }

    /**
     * check if there is no direct link or there is no stores,
     *
     */
    private fun checkIfDirectAndStoreAvailable(list: List<UpdaterStoreList>) =
        list.map {
            it.store
        }
            .distinct()
            .toList()
            .partition {
                it == Store.DIRECT_URL
            }.run {
                first.isNotEmpty() && second.isNotEmpty()
            }

    /**
     * listener to react to user, when user clicks on a store
     */
    private fun onListListener(item: UpdaterStoreList) {
        when (item.store) {
            Store.DIRECT_URL ->
                DirectLinkDownload().getApk(item.url, activity, fragmentManager)
            Store.GOOGLE_PLAY ->
                GooglePlayStore().setStoreData(context, item)
            Store.CAFE_BAZAAR ->
                CafeBazaarStore().setStoreData(context, item)
            Store.MYKET ->
                MyketStore().setStoreData(context, item)
            Store.IRAN_APPS ->
                IranAppsStore().setStoreData(context, item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constants.typeface = null
    }

    companion object {

        // fragment variable to make this dialog singleton
        private val fragment = AppUpdaterDialog()

        /**
         * get Instance method
         */
        fun getInstance(
            title: String? = "",
            description: String? = "",
            list: List<UpdaterStoreList>,
            isForce: Boolean = false,
            typeface: Typeface? = null
        ): AppUpdaterDialog {

            //set typeface in utils class to use later in application
            Constants.typeface = typeface

            // bundle to add data to our dialog
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list, !isForce)
            bundle.putParcelable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
