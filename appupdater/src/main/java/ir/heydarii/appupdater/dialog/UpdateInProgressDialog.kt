package ir.heydarii.appupdater.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.utils.typeface

/**
 * Dialog to show download progress to user
 */
class UpdateInProgressDialog : DialogFragment() {

    companion object {
        var instance = UpdateInProgressDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_update_in_progress_dialog, container, false)
        return view.apply {
            typeface?.let {
                findViewById<TextView>(R.id.txtTitle).typeface = it
                findViewById<TextView>(R.id.txtDescription).typeface = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // making width of dialog to match_parent
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }
}