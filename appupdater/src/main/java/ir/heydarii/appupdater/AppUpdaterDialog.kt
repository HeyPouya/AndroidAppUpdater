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
import java.lang.Exception


const val TITLE = "TITLE"
const val DATA_LIST = "DATA_LIST"

class AppUpdaterDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val cancelableMode = data?.isForceUpdate
        setDialogCancelable(cancelableMode)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_updater_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

    }

    private fun getData() {
        val data = arguments?.getParcelable<UpdaterFragmentModel>(DATA_LIST)
        val title = data?.title
        val description = data?.description
        val list = data?.list
        setUpProperties(title, description, list)
    }

    private fun setDialogCancelable(cancelableMode: Boolean?) {
        cancelableMode?.let { isCancelable = it }
    }

    private fun setUpProperties(title: String?, description: String?, list: List<UpdaterStoreList>?) {
        txtTitle.text = title
        txtDescription.text = description
        recycler.adapter = StoresRecyclerAdapter(list.orEmpty()) { onListListener(it) }
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun onListListener(item: UpdaterStoreList) {
        when (item.store) {
            Store.DIRECT_URL -> {
                //TODO : download app
            }
            Store.GOOGLE_PLAY -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${item.packageName}")))
                } catch (e: Exception) {
                    showUrlOrException(item, Store.GOOGLE_PLAY)
                }

            }
            Store.CAFE_BAZAAR -> {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("bazaar://details?id=${item.packageName}")
                intent.setPackage("com.farsitel.bazaar")
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    showUrlOrException(item, Store.CAFE_BAZAAR)
                }

            }
            Store.MYKET -> {
            }
            Store.IRAN_APPS -> {
            }
        }
    }

    private fun showUrlOrException(item: UpdaterStoreList, store: Store) {
        if (item.url.isNotEmpty())
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        else
            Toast.makeText(context, "Please install ${store.name.toLowerCase()}", Toast.LENGTH_LONG).show()
    }

    companion object {
        private val fragment = AppUpdaterDialog()

        fun getInstance(title: String = "", description: String = "", list: List<UpdaterStoreList>, isForce: Boolean = false): AppUpdaterDialog {
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list, !isForce)
            bundle.putParcelable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
