package com.pouyaheydari.appupdater

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.appupdater.core.pojo.Store.DIRECT_URL
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterFragmentModel
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.getApk
import com.pouyaheydari.appupdater.core.utils.serializable
import com.pouyaheydari.appupdater.core.utils.typeface

private const val DATA_LIST = "DATA_LIST"
private const val UPDATE_DIALOG_TAG = "UpdateDialog"

/**
 * Shows ForceUpdate Dialog Fragment
 */
class AppUpdaterDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Getting data passed to the library
        val data = arguments?.serializable<UpdaterFragmentModel>(DATA_LIST)

        // Setting isCancelable
        data?.isForceUpdate?.let { isCancelable = it }

        val dialogBackground = data?.theme?.let {
            when (it) {
                Theme.LIGHT -> R.drawable.dialog_background
                Theme.DARK -> R.drawable.dialog_background_dark
            }
        } ?: R.drawable.dialog_background

        // Set background for the dialog
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), dialogBackground)
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val view = inflater.inflate(R.layout.fragment_app_updater_dialog, container, false)
        return view.apply {
            typeface?.let {
                findViewById<TextView>(R.id.txtTitle).typeface = it
                findViewById<TextView>(R.id.txtDescription).typeface = it
                findViewById<TextView>(R.id.txtOr).typeface = it
                findViewById<TextView>(R.id.txtStore).typeface = it
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // make dialog's width matchParent
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getData that user set's via constructor
        getData()
    }

    private fun getData() {
        val data = arguments?.serializable<UpdaterFragmentModel>(DATA_LIST)
        val title = data?.title
        val description = data?.description
        val list = data?.list
        setTheme(data?.theme)
        checkNotNull(list)
        setUpProperties(title, description, list, data?.theme)
    }

    private fun setTheme(theme: Theme?) {
        val textColor = when (theme ?: Theme.LIGHT) {
            Theme.LIGHT -> R.color.appupdater_text_colors
            Theme.DARK -> R.color.appupdater_text_colors_dark
        }
        with(requireView()) {
            findViewById<TextView>(R.id.txtTitle)?.setTextColor(getColor(requireContext(), textColor))
            findViewById<TextView>(R.id.txtDescription)?.setTextColor(getColor(requireContext(), textColor))
            findViewById<TextView>(R.id.txtOr)?.setTextColor(getColor(requireContext(), textColor))
            findViewById<TextView>(R.id.txtStore)?.setTextColor(getColor(requireContext(), textColor))
            findViewById<View>(R.id.leftOrLine)?.setBackgroundColor(getColor(requireContext(), textColor))
            findViewById<View>(R.id.rightOrLine)?.setBackgroundColor(getColor(requireContext(), textColor))
        }
    }

    private fun setUpProperties(title: String?, description: String?, list: List<UpdaterStoreList>, theme: Theme?) {
        requireView().findViewById<TextView>(R.id.txtTitle)?.text = title
        requireView().findViewById<TextView>(R.id.txtDescription)?.text = description

        hideOrLayoutIfNeeded(checkIfDirectAndStoreAvailable(list))

        setUpBothRecyclers(list, theme)
    }

    private fun setUpBothRecyclers(list: List<UpdaterStoreList>, theme: Theme?) {
        val directLinks = list.filter { it.store == DIRECT_URL }
        val storeLinks = list.filterNot { it.store == DIRECT_URL }

        if (directLinks.isNotEmpty()) {
            requireView().findViewById<RecyclerView>(R.id.recyclerDirect)?.adapter =
                DirectRecyclerAdapter(directLinks) { onListListener(it) }
        }

        if (storeLinks.isNotEmpty()) {
            requireView().findViewById<RecyclerView>(R.id.recyclerStores)?.adapter =
                StoresRecyclerAdapter(storeLinks, theme) { onListListener(it) }
        }
    }

    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        requireView().findViewById<LinearLayout>(R.id.linearLayout).isVisible = storeAndDirectAvailable
    }

    private fun checkIfDirectAndStoreAvailable(list: List<UpdaterStoreList>) =
        list.map { it.store }
            .distinct()
            .toList()
            .partition {
                it == DIRECT_URL
            }.run {
                first.isNotEmpty() && second.isNotEmpty()
            }

    private fun onListListener(item: UpdaterStoreList) {
        when (item.store) {
            DIRECT_URL -> getApk(item.url, activity) { shouldShowUpdateInProgress ->
                when (shouldShowUpdateInProgress) {
                    true -> showUpdateInProgressDialog()
                    false -> hideUpdateInProgressDialog()
                }
            }
            else -> item.store.provider?.newInstance()?.setStoreData(context, item)
        }
    }

    private fun showUpdateInProgressDialog() {
        UpdateInProgressDialog.instance.show(parentFragmentManager, UPDATE_DIALOG_TAG)
    }

    private fun hideUpdateInProgressDialog() {
        if (UpdateInProgressDialog.instance.isAdded)
            UpdateInProgressDialog.instance.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        typeface = null
    }

    companion object {

        // fragment variable to make this dialog singleton
        private val fragment = AppUpdaterDialog()

        /**
         * get Instance method
         */
        fun getInstance(
            title: String = "",
            description: String = "",
            list: List<UpdaterStoreList> = listOf(),
            isForce: Boolean = false,
            tf: Typeface? = null,
            theme: Theme = Theme.LIGHT
        ): AppUpdaterDialog {

            // set typeface in utils class to use later in application
            typeface = tf

            // bundle to add data to our dialog
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, list, !isForce, theme = theme)
            bundle.putSerializable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
