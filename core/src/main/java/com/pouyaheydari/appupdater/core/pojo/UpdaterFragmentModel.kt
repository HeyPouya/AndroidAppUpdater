package com.pouyaheydari.appupdater.core.pojo

import android.graphics.Typeface
import java.io.Serializable

/**
 * User has to pass this model to the library
 */
data class UpdaterFragmentModel(
    var title: String = "",
    var description: String = "",
    var list: List<UpdaterStoreList> = listOf(),
    var isForceUpdate: Boolean = false,
    var typeface: Typeface? = null,
    var theme: Theme = Theme.LIGHT,
) : Serializable {

    companion object {
        val EMPTY = UpdaterFragmentModel()
    }
}
