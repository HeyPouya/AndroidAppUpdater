package com.pouyaheydari.appupdater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.utils.serializable
import com.pouyaheydari.appupdater.core.utils.tf
import com.pouyaheydari.appupdater.databinding.FragmentUpdateInProgressDialogBinding

private const val THEME = "THEME"

/**
 * Dialog to show download in progress to user
 */
internal class UpdateInProgressDialog : DialogFragment() {

    private var _binding: FragmentUpdateInProgressDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpdateInProgressDialogBinding.inflate(inflater, container, false)
        val data = arguments?.serializable(THEME) ?: Theme.LIGHT
        setDialogBackground(data)
        setTheme(data)
        return binding.root
    }

    private fun setTheme(data: Theme) {
        val textColor = when (data) {
            Theme.LIGHT -> R.color.appupdater_text_colors
            Theme.DARK -> R.color.appupdater_text_colors_dark
        }

        with(binding) {
            txtTitle.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            txtDescription.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        }
    }

    private fun setDialogBackground(data: Theme) {
        val dialogBackground = when (data) {
            Theme.LIGHT -> R.drawable.dialog_background
            Theme.DARK -> R.drawable.update_in_progress_dialog_background_dark
        }

        // Set background for the dialog
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), dialogBackground),
        )
    }

    companion object {
        private val fragment = UpdateInProgressDialog()

        fun getInstance(theme: Theme?): UpdateInProgressDialog {
            val bundle = Bundle()
            bundle.putSerializable(THEME, theme)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tf?.let {
                txtTitle.typeface = it
                txtDescription.typeface = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // making width of dialog to match_parent
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
