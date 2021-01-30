package ir.heydarii.appupdater.utils

import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojo.UpdaterFragmentModel
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * This inline function helps building stores in DSL way
 */
inline fun store(block: UpdaterStoreList.() -> Unit): UpdaterStoreList {
    return UpdaterStoreList().apply(block)
}

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