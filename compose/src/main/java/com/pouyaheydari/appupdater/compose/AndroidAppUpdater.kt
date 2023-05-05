package com.pouyaheydari.appupdater.compose

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pouyaheydari.appupdater.compose.ui.DialogHeaderComponent
import com.pouyaheydari.appupdater.compose.ui.DirectDownloadLinkComponent
import com.pouyaheydari.appupdater.compose.ui.DividerComponent
import com.pouyaheydari.appupdater.compose.ui.SquareStoreItemComponent
import com.pouyaheydari.appupdater.compose.ui.UpdateInProgressDialogComponent
import com.pouyaheydari.appupdater.compose.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.compose.utils.getActivity
import com.pouyaheydari.appupdater.compose.utils.storeList
import com.pouyaheydari.appupdater.core.pojo.DialogStates
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.utils.TAG
import com.pouyaheydari.appupdater.core.utils.getApk
import com.pouyaheydari.appupdater.core.utils.shouldShowStoresDivider

@Composable
fun AndroidAppUpdater(
    dialogTitle: String = "",
    dialogDescription: String = "",
    storeList: List<StoreListItem> = listOf(),
    onDismissRequested: () -> Unit = {},
    typeface: Typeface? = null,
    theme: Theme = Theme.LIGHT,
) {
    val viewModel: AndroidAppUpdaterViewModel = viewModel()
    val activity = LocalContext.current.getActivity()

    AndroidAppUpdaterTheme(darkTheme = theme == Theme.DARK) {
        Dialog(onDismissRequest = { onDismissRequested() }) {
            DialogContent(dialogTitle, dialogDescription, storeList, typeface, viewModel::onListListener)
        }

        when (val value = viewModel.shouldShowUpdateInProgress.collectAsState().value) {
            is DialogStates.DownloadApk -> {
                if (value.apkUrl == null) {
                    Log.e(TAG, "Download url is null. Skipping downloading the apk")
                } else if (activity == null) {
                    Log.e(TAG, "Provided activity is null. Skipping downloading the apk")
                } else {
                    getApk(value.apkUrl!!, activity)
                }
            }

            DialogStates.HideUpdateInProgress -> {}
            is DialogStates.OpenStore -> {
                value.store?.showStore(activity)
            }

            DialogStates.ShowUpdateInProgress -> UpdateInProgressDialogComponent()
        }
    }
}

@Composable
fun DialogContent(
    dialogTitle: String,
    dialogDescription: String,
    storeList: List<StoreListItem>,
    typeface: Typeface?,
    onClickListener: (StoreListItem) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { DialogHeaderComponent(dialogTitle, typeface, dialogDescription) }
            storeList.filter { it.store == Store.DIRECT_URL }.forEach {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    DirectDownloadLinkComponent(it, onClickListener)
                }
            }
            if (shouldShowStoresDivider(storeList)) {
                item(span = { GridItemSpan(maxLineSpan) }) { DividerComponent() }
            }
            storeList.filter { it.store != Store.DIRECT_URL }.forEach {
                item { SquareStoreItemComponent(it, onClickListener) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightPreview() {
    AndroidAppUpdater(
        dialogTitle = stringResource(id = R.string.appupdater_app_name),
        dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
        storeList = storeList,
        theme = Theme.LIGHT,
    )
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    AndroidAppUpdater(
        dialogTitle = stringResource(id = R.string.appupdater_app_name),
        dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
        storeList = storeList,
        theme = Theme.DARK,
    )
}
