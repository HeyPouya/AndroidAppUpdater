package ir.heydarii.appupdater.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.utils.Utils
import kotlinx.android.synthetic.main.fragment_update_in_progress_dialog.*

/**
 * Dialog to show download progress to user
 */
class UpdateInProgressDialog : DialogFragment() {

    companion object {

        var instance = UpdateInProgressDialog()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_in_progress_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()

        if (Utils.typeface != null) {
            txtTitle.typeface = Utils.typeface
            txtDescription.typeface = Utils.typeface
        }
        //making width of dialog to match_parent
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

}
