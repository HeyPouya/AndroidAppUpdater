package com.pouyaheydari.appupdater.main.dsl

import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.main.ui.AppUpdaterDialog
import com.pouyaheydari.appupdater.main.ui.model.UpdaterDialogData
import com.pouyaheydari.appupdater.store.domain.StoreListItem

/**
 * This inline function helps building stores in DSL way
 */
inline fun store(block: StoreListItem.() -> Unit): StoreListItem = StoreListItem().apply(block)

/**
 * This inline function helps building direct download links in DSL way
 */
inline fun directDownload(block: DirectDownloadListItem.() -> Unit): DirectDownloadListItem = DirectDownloadListItem().apply(block)

/**
 * This inline function helps building UpdateDialog in DSL way
 */
inline fun updateDialogBuilder(block: UpdaterDialogData.() -> Unit): AppUpdaterDialog =
    AppUpdaterDialog.getInstance(UpdaterDialogData().apply(block))
