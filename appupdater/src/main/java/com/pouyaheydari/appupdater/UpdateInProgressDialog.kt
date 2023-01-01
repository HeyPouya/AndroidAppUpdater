package com.pouyaheydari.appupdater

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.DialogFragment
import com.pouyaheydari.appupdater.core.utils.tf

/**
 * Dialog to show download progress to user
 */
internal class UpdateInProgressDialog : DialogFragment(R.layout.fragment_update_in_progress_dialog) {

    companion object {
        var instance = UpdateInProgressDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            tf?.let {
                findViewById<TextView>(R.id.txtTitle).typeface = it
                findViewById<TextView>(R.id.txtDescription).typeface = it
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // making width of dialog to match_parent
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }
}
