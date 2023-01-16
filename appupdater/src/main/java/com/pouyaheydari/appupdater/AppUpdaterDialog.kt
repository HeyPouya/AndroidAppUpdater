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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.pouyaheydari.appupdater.adapters.DirectRecyclerAdapter
import com.pouyaheydari.appupdater.adapters.StoresRecyclerAdapter
import com.pouyaheydari.appupdater.core.pojo.Store.DIRECT_URL
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.utils.serializable
import com.pouyaheydari.appupdater.core.utils.shouldShowStoresDivider
import com.pouyaheydari.appupdater.databinding.FragmentAppUpdaterDialogBinding
import com.pouyaheydari.appupdater.pojo.UpdaterFragmentModel
import com.pouyaheydari.appupdater.utils.TypefaceHolder
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

private const val UPDATE_DIALOG_KEY = "UPDATE_DIALOG_KEY"
private const val UPDATE_DIALOG_TAG = "UPDATE_DIALOG_TAG"
private const val UPDATE_DIALOG_README_URL = "https://github.com/SirLordPouya/AndroidAppUpdater"

/**
 * Shows ForceUpdate Dialog Fragment
 */
class AppUpdaterDialog : DialogFragment() {

    private val viewModel: AppUpdaterViewModel by viewModels()

    private var _binding: FragmentAppUpdaterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Getting data passed to the library
        val data = arguments?.serializable(UPDATE_DIALOG_KEY) ?: UpdaterFragmentModel.EMPTY
        if (data == UpdaterFragmentModel.EMPTY || data.storeLis.isEmpty()) {
            throw IllegalArgumentException("It seems you forgot to add any data to the updater dialog. Add the data as described in $UPDATE_DIALOG_README_URL")
        }
        setDialogBackground(data.theme)
        isCancelable = data.isForceUpdate

        _binding = FragmentAppUpdaterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setDialogBackground(theme: Theme) {
        val dialogBackground = when (theme) {
            Theme.LIGHT -> R.drawable.dialog_background
            Theme.DARK -> R.drawable.dialog_background_dark
        }
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), dialogBackground),
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getData that user set's via constructor
        getData()
    }

    private fun subscribeToUpdateInProgressDialog(theme: Theme) {
        lifecycleScope.launchWhenCreated {
            viewModel.updateInProgressState.asStateFlow().collectLatest {
                when (it) {
                    true -> showUpdateInProgressDialog(theme)
                    false -> hideUpdateInProgressDialog()
                }
            }
        }
    }

    private fun getData() {
        val data = arguments?.serializable(UPDATE_DIALOG_KEY) ?: UpdaterFragmentModel.EMPTY
        val title = data.title
        val description = data.description
        val list = data.storeLis
        setTheme(data.theme)
        val typeface = TypefaceHolder.getTypeface()
        setTypeface(typeface)
        setUpProperties(title, description, list, data.theme, typeface)
        subscribeToUpdateInProgressDialog(data.theme)
    }

    private fun setTypeface(typeface: Typeface?) {
        typeface?.let {
            binding.txtTitle.typeface = it
            binding.txtDescription.typeface = it
            binding.txtOr.typeface = it
            binding.txtStore.typeface = it
        }
    }

    private fun setTheme(theme: Theme) {
        val textColor = when (theme) {
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

    private fun setUpProperties(title: String?, description: String?, list: List<StoreListItem>, theme: Theme, typeface: Typeface?) {
        binding.txtTitle.text = title
        binding.txtDescription.text = description

        hideOrLayoutIfNeeded(shouldShowStoresDivider(list))

        setUpBothRecyclers(list, theme, typeface)
    }

    private fun setUpBothRecyclers(list: List<StoreListItem>, theme: Theme, typeface: Typeface?) {
        val directLinks = list.filter { it.store == DIRECT_URL }
        val storeLinks = list.filterNot { it.store == DIRECT_URL }

        if (directLinks.isNotEmpty()) {
            binding.recyclerDirect.adapter = DirectRecyclerAdapter(directLinks, typeface) { viewModel.onListListener(it, requireActivity()) }
        }

        if (storeLinks.isNotEmpty()) {
            binding.recyclerStores.adapter = StoresRecyclerAdapter(storeLinks, theme, typeface) { viewModel.onListListener(it, requireActivity()) }
        }
    }

    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        binding.linearLayout.isVisible = storeAndDirectAvailable
    }

    private fun showUpdateInProgressDialog(theme: Theme) {
        if (parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) == null && requireActivity().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            val fragment = UpdateInProgressDialog()
            val bundle = Bundle()
            bundle.putSerializable(THEME, theme)
            fragment.arguments = bundle
            fragment.show(parentFragmentManager, UPDATE_DIALOG_TAG)
        }
    }

    private fun hideUpdateInProgressDialog() {
        if (parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) != null && requireActivity().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            val dialog = parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) as UpdateInProgressDialog
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        /**
         * @param title Title of the dialog
         * @param description Description that is shown below the title
         * @param storeList List of all stores that user can update your app from (including [com.pouyaheydari.appupdater.core.pojo.Store.DIRECT_URL])
         * @param isForce Should the user be able to close the dialog?
         * @param typeface Typeface to be used in text views
         *
         * @return a new instance of [AppUpdaterDialog]
         */
        fun getInstance(
            title: String,
            description: String,
            storeList: List<StoreListItem>,
            isForce: Boolean = false,
            typeface: Typeface? = null,
            theme: Theme = Theme.LIGHT,
        ): AppUpdaterDialog {
            val fragment = AppUpdaterDialog()

            TypefaceHolder.setTypeface(typeface)
            // bundle to add data to our dialog
            val bundle = Bundle()
            val data = UpdaterFragmentModel(title, description, storeList, !isForce, theme)
            bundle.putSerializable(UPDATE_DIALOG_KEY, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}
