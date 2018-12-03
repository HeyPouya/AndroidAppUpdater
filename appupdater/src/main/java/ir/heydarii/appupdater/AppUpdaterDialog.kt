package ir.heydarii.appupdater


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.appupdater.pojomodel.UpdaterFragmentModel
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList
import kotlinx.android.synthetic.main.fragment_app_updater_dialog.*

const val TITLE = "TITLE"
const val DATA_LIST = "DATA_LIST"

class AppUpdaterDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    private fun setUpProperties(title: String?, description: String?, list: List<UpdaterStoreList>?) {
        txtTitle.text = title
        txtDescription.text = description
        recycler.adapter = StoresRecyclerAdapter(list.orEmpty())
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    companion object {
        private val fragment = AppUpdaterDialog()

        fun getInstance(title: String = "", description: String = "", list: List<UpdaterStoreList>) {
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list)
            bundle.putParcelable(DATA_LIST, data)
            fragment.arguments = bundle
        }
    }
}
