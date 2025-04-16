package com.pouyaheydari.appupdater.main.ui

import android.app.DownloadManager
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.directdownload.utils.donwloadapk.checkPermissionsAndDownloadApk
import com.pouyaheydari.appupdater.directdownload.utils.installapk.installAPK
import com.pouyaheydari.appupdater.main.R
import com.pouyaheydari.appupdater.main.data.mapper.mapToSelectedTheme
import com.pouyaheydari.appupdater.main.databinding.FragmentAppUpdaterDialogBinding
import com.pouyaheydari.appupdater.main.ui.adapters.DirectRecyclerAdapter
import com.pouyaheydari.appupdater.main.ui.adapters.StoresRecyclerAdapter
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenIntents
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenStates
import com.pouyaheydari.appupdater.main.ui.model.UpdaterDialogData
import com.pouyaheydari.appupdater.main.ui.model.UpdaterFragmentModel
import com.pouyaheydari.appupdater.main.ui.model.UserSelectedTheme
import com.pouyaheydari.appupdater.main.utils.ErrorCallbackHolder
import com.pouyaheydari.appupdater.main.utils.TypefaceHolder
import com.pouyaheydari.appupdater.main.utils.getDialogWidth
import com.pouyaheydari.appupdater.main.utils.parcelable
import com.pouyaheydari.appupdater.main.utils.putEnum
import com.pouyaheydari.appupdater.store.domain.AppStoreCallback
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import com.pouyaheydari.appupdater.store.domain.showAppInSelectedStore
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val UPDATE_DIALOG_KEY = "UPDATE_DIALOG_KEY"
private const val UPDATE_DIALOG_TAG = "UPDATE_DIALOG_TAG"
private const val UPDATE_DIALOG_README_URL = "https://github.com/HeyPouya/AndroidAppUpdater"

/**
 * Shows ForceUpdate Dialog Fragment
 */
class AppUpdaterDialog : DialogFragment() {
    private val viewModel: AppUpdaterViewModel by viewModels(factoryProducer = { AppUpdaterViewModelFactory() })

    private var appUpdaterDialogBinding: FragmentAppUpdaterDialogBinding? = null
    private val binding get() = appUpdaterDialogBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Getting data passed to the library
        val data = arguments?.parcelable(UPDATE_DIALOG_KEY) ?: UpdaterFragmentModel.EMPTY
        if (data == UpdaterFragmentModel.EMPTY || (data.storeList.isEmpty() && data.directDownloadList.isEmpty())) {
            throw IllegalArgumentException("Invalid data provided to the updater dialog. Either 'storeList' or 'directDownloadList' must be non-empty. For more details, refer to the documentation at $UPDATE_DIALOG_README_URL")
        }
        setDialogBackground(mapToSelectedTheme(data.theme, requireContext()))
        isCancelable = data.isForceUpdate

        appUpdaterDialogBinding =
            FragmentAppUpdaterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setDialogBackground(theme: UserSelectedTheme) {
        val dialogBackground = when (theme) {
            UserSelectedTheme.LIGHT -> R.drawable.dialog_background
            UserSelectedTheme.DARK -> R.drawable.dialog_background_dark
        }
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), dialogBackground),
        )
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(getDialogWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getData that user set's via constructor
        getData()
    }

    private fun subscribeToViewModel(theme: UserSelectedTheme) {
        viewModel.screenState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                when (it) {
                    is DialogScreenStates.DownloadApk -> {
                        checkPermissionsAndDownloadApk(
                            url = it.apkUrl,
                            activity = requireActivity(),
                            androidSdkVersion = Build.VERSION.SDK_INT,
                            notificationTitle = requireContext().getString(com.pouyaheydari.appupdater.directdownload.R.string.appupdater_download_notification_title),
                            notificationDescription = requireContext().getString(com.pouyaheydari.appupdater.directdownload.R.string.appupdater_download_notification_desc),
                            downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        ) {
                            viewModel.handleIntent(DialogScreenIntents.OnApkDownloadStarted)
                        }
                        viewModel.handleIntent(DialogScreenIntents.OnApkDownloadRequested)
                    }

                    is DialogScreenStates.OpenStore -> showAppInSelectedStore(context, it.store) { storeCallback ->
                        onStoreCallback(storeCallback)
                    }

                    is DialogScreenStates.ExecuteErrorCallback -> {
                        ErrorCallbackHolder.callback?.invoke(it.storeName)
                        viewModel.handleIntent(DialogScreenIntents.OnErrorCallbackExecuted)
                    }

                    DialogScreenStates.HideUpdateInProgress -> hideUpdateInProgressDialog()
                    DialogScreenStates.ShowUpdateInProgress -> showUpdateInProgressDialog(theme)
                    DialogScreenStates.Empty -> hideUpdateInProgressDialog()
                    is DialogScreenStates.InstallApk -> installDownloadedApk(it)
                }
            }.launchIn(lifecycleScope)
    }

    private fun installDownloadedApk(it: DialogScreenStates.InstallApk) {
        hideUpdateInProgressDialog()
        installAPK(requireActivity(), it.apk, Build.VERSION.SDK_INT)
        viewModel.handleIntent(DialogScreenIntents.OnApkInstallationStarted)
    }

    private fun onStoreCallback(storeCallback: AppStoreCallback) {
        when (storeCallback) {
            is AppStoreCallback.Failure -> viewModel.handleIntent(DialogScreenIntents.OnOpeningStoreFailed(storeCallback.store))
            is AppStoreCallback.Success -> viewModel.handleIntent(DialogScreenIntents.OnStoreOpened)
        }
    }

    private fun getData() {
        val data = arguments?.parcelable(UPDATE_DIALOG_KEY) ?: UpdaterFragmentModel.EMPTY
        val title = data.title
        val description = data.description
        val storeList = data.storeList
        val directDownloadList = data.directDownloadList
        val theme = mapToSelectedTheme(data.theme, requireContext())
        setTheme(theme)
        val typeface = TypefaceHolder.typeface
        setTypeface(typeface)
        setUpProperties(title, description, storeList, directDownloadList, theme, typeface)
        subscribeToViewModel(theme)
    }

    private fun setTypeface(typeface: Typeface?) {
        typeface?.let {
            binding.txtTitle.typeface = it
            binding.txtDescription.typeface = it
            binding.txtOr.typeface = it
            binding.txtStore.typeface = it
        }
    }

    private fun setTheme(theme: UserSelectedTheme) {
        val textColor = when (theme) {
            UserSelectedTheme.LIGHT -> R.color.appupdater_text_colors
            UserSelectedTheme.DARK -> R.color.appupdater_text_colors_dark
        }
        with(binding) {
            txtTitle.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            txtDescription.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            txtOr.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            txtStore.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            leftOrLine.setBackgroundColor(ContextCompat.getColor(requireContext(), textColor))
            rightOrLine.setBackgroundColor(ContextCompat.getColor(requireContext(), textColor))
        }
    }

    private fun setUpProperties(
        title: String?,
        description: String?,
        storeList: List<StoreListItem>,
        directDownloadList: List<DirectDownloadListItem>,
        theme: UserSelectedTheme,
        typeface: Typeface?,
    ) {
        binding.txtTitle.text = title
        binding.txtDescription.text = description

        hideOrLayoutIfNeeded(shouldShowStoresDivider(directDownloadList, storeList))

        setUpBothRecyclers(directDownloadList, storeList, theme, typeface)
    }

    private fun shouldShowStoresDivider(directDownloadList: List<DirectDownloadListItem>, storeList: List<StoreListItem>): Boolean =
        directDownloadList.isNotEmpty() && storeList.isNotEmpty()

    private fun setUpBothRecyclers(
        directDownloadList: List<DirectDownloadListItem>,
        storeList: List<StoreListItem>,
        theme: UserSelectedTheme,
        typeface: Typeface?,
    ) {
        if (directDownloadList.isNotEmpty()) {
            binding.recyclerDirect.adapter = DirectRecyclerAdapter(directDownloadList, typeface) {
                viewModel.handleIntent(DialogScreenIntents.OnDirectLinkClicked(it))
            }
        }

        if (storeList.isNotEmpty()) {
            val spanCount = if (storeList.size > 1) 2 else 1
            binding.recyclerStores.layoutManager = GridLayoutManager(requireContext(), spanCount)
            binding.recyclerStores.adapter = StoresRecyclerAdapter(storeList, theme, typeface) {
                viewModel.handleIntent(DialogScreenIntents.OnStoreClicked(it))
            }
        }
    }

    private fun hideOrLayoutIfNeeded(storeAndDirectAvailable: Boolean) {
        binding.linearLayout.isVisible = storeAndDirectAvailable
    }

    private fun showUpdateInProgressDialog(theme: UserSelectedTheme) {
        if (parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) == null &&
            requireActivity().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
        ) {
            val fragment = UpdateInProgressDialog()
            val bundle = Bundle().apply {
                putEnum(THEME, theme)
            }
            fragment.arguments = bundle
            fragment.show(parentFragmentManager, UPDATE_DIALOG_TAG)
        }
    }

    private fun hideUpdateInProgressDialog() {
        if (parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) != null &&
            requireActivity().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
        ) {
            val dialog = parentFragmentManager.findFragmentByTag(UPDATE_DIALOG_TAG) as UpdateInProgressDialog
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appUpdaterDialogBinding = null
    }

    companion object {
        /**
         * @param dialogData Data to be shown in the dialog
         *
         * @return a new instance of [AppUpdaterDialog]
         */
        fun getInstance(dialogData: UpdaterDialogData): AppUpdaterDialog = with(dialogData) {
            val fragment = AppUpdaterDialog()
            val data = UpdaterFragmentModel(
                title,
                description,
                storeList,
                directDownloadList,
                !isForceUpdate,
                theme
            )

            TypefaceHolder.typeface = typeface
            ErrorCallbackHolder.callback = errorWhileOpeningStoreCallback
            // bundle to add data to our dialog
            val bundle = Bundle().apply { putParcelable(UPDATE_DIALOG_KEY, data) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
