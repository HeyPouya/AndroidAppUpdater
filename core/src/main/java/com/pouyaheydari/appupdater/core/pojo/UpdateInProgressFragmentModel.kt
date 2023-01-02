package com.pouyaheydari.appupdater.core.pojo

import android.graphics.Typeface
import java.io.Serializable

/**
 * User has to pass this model to the library
 */
data class UpdateInProgressFragmentModel(
    var typeface: Typeface? = null,
    var theme: Theme = Theme.LIGHT,
) : Serializable {

    companion object {
        val EMPTY = UpdateInProgressFragmentModel(null, Theme.LIGHT)
    }
}
