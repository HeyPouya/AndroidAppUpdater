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
import com.pouyaheydari.appupdater.databinding.FragmentUpdateInProgressDialogBinding
import com.pouyaheydari.appupdater.utils.TypefaceHolder

const val THEME = "THEME"

/**
 * Dialog to show download in progress to user
 */
internal class UpdateInProgressDialog : DialogFragment() {

    private var _binding: FragmentUpdateInProgressDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpdateInProgressDialogBinding.inflate(inflater, container, false)
        val theme = arguments?.serializable(THEME) ?: Theme.LIGHT
        setDialogBackground(theme)
        setTheme(theme)
        isCancelable = false
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTypeface()
    }

    private fun setTypeface() {
        val typeface = TypefaceHolder.getTypeface()
        with(binding) {
            typeface?.let {
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
