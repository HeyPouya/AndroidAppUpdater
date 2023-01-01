package com.pouyaheydari.appupdater

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.pouyaheydari.appupdater.adapters.DirectRecyclerAdapter
import com.pouyaheydari.appupdater.adapters.StoresRecyclerAdapter
import com.pouyaheydari.appupdater.core.pojo.Store.DIRECT_URL
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterFragmentModel
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.areDirectAndStoresAvailable
import com.pouyaheydari.appupdater.core.utils.getApk
import com.pouyaheydari.appupdater.core.utils.serializable
import com.pouyaheydari.appupdater.core.utils.tf
import com.pouyaheydari.appupdater.databinding.FragmentAppUpdaterDialogBinding

private const val DATA_LIST = "DATA_LIST"
private const val UPDATE_DIALOG_TAG = "UpdateDialog"

/**
 * Shows ForceUpdate Dialog Fragment
 */
class AppUpdaterDialog : DialogFragment() {

    private var _binding: FragmentAppUpdaterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            ContextCompat.getDrawable(requireContext(), dialogBackground),
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        _binding = FragmentAppUpdaterDialogBinding.inflate(inflater, container, false)
        return binding.root.apply {
            tf?.let {
                binding.txtTitle.typeface = it
                binding.txtDescription.typeface = it
                binding.txtOr.typeface = it
                binding.txtStore.typeface = it
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
        setUpProperties(title, description, list, data.theme)
    }

    private fun setTheme(theme: Theme?) {
        val textColor = when (theme ?: Theme.LIGHT) {
            Theme.LIGHT -> R.color.appupdater_text_colors
            Theme.DARK -> R.color.appupdater_text_colors_dark
        }
        with(binding) {
            txtTitle.setTextColor(getColor(requireContext(), textColor))
            txtDescription.setTextColor(getColor(requireContext(), textColor))
            txtOr.setTextColor(getColor(requireContext(), textColor))
            txtStore.setTextColor(getColor(requireContext(), textColor))
            leftOrLine.setBackgroundColor(getColor(requireContext(), textColor))
            rightOrLine.setBackgroundColor(getColor(requireContext(), textColor))
        }
    }

    private fun setUpProperties(title: String?, description: String?, list: List<UpdaterStoreList>, theme: Theme?) {
        binding.txtTitle.text = title
        binding.txtDescription.text = description

        hideOrLayoutIfNeeded(areDirectAndStoresAvailable(list))

        setUpBothRecyclers(list, theme)
    }

    private fun setUpBothRecyclers(list: List<UpdaterStoreList>, theme: Theme?) {
        val directLinks = list.filter { it.store == DIRECT_URL }
        val storeLinks = list.filterNot { it.store == DIRECT_URL }

        if (directLinks.isNotEmpty()) {
            binding.recyclerDirect.adapter = DirectRecyclerAdapter(directLinks) { onListListener(it) }
        }

        if (storeLinks.isNotEmpty()) {
            binding.recyclerStores.adapter = StoresRecyclerAdapter(storeLinks, theme) { onListListener(it) }
        }
    }

    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        binding.linearLayout.isVisible = storeAndDirectAvailable
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
        if (UpdateInProgressDialog.instance.isAdded) {
            UpdateInProgressDialog.instance.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        tf = null
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
            storeList: List<UpdaterStoreList> = listOf(),
            isForce: Boolean = false,
            typeface: Typeface? = null,
            theme: Theme = Theme.LIGHT,
        ): AppUpdaterDialog {
            // set typeface in utils class to use later in application
            tf = typeface

            // bundle to add data to our dialog
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, storeList, !isForce, theme = theme)
            bundle.putSerializable(DATA_LIST, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
