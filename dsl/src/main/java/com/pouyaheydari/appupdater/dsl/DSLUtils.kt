package com.pouyaheydari.appupdater.dsl

import com.pouyaheydari.appupdater.core.pojo.UpdaterFragmentModel
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.AppUpdaterDialog

/**
 * This inline function helps building stores in DSL way
 */
inline fun store(block: UpdaterStoreList.() -> Unit): UpdaterStoreList = UpdaterStoreList().apply(block)

/**
 * This inline function helps building UpdateDialog in DSL way
 */
inline fun updateDialogBuilder(block: UpdaterFragmentModel.() -> Unit): AppUpdaterDialog {
    val updaterModel = UpdaterFragmentModel().apply(block)
    with(updaterModel) {
        return AppUpdaterDialog.getInstance(
            title,
            description,
            list ?: listOf(),
            isForceUpdate ?: false,
            typeface
        )
    }
}
