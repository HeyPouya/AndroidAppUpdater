package com.pouyaheydari.appupdater.compose

import android.app.Activity
import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pouyaheydari.appupdater.compose.ui.DividerComponent
import com.pouyaheydari.appupdater.compose.ui.UpdateInProgressDialogComponent
import com.pouyaheydari.appupdater.compose.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.compose.ui.theme.Blue
import com.pouyaheydari.appupdater.compose.utils.getActivity
import com.pouyaheydari.appupdater.compose.utils.storeList
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.pojo.Theme
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

    AndroidAppUpdaterTheme(darkTheme = theme == Theme.DARK) {
        Dialog(onDismissRequest = { onDismissRequested() }) {
            DialogContent(dialogTitle, dialogDescription, storeList, typeface, viewModel::onListListener)
        }
        if (viewModel.state.value) {
            UpdateInProgressDialogComponent()
        }
    }
}

@Composable
fun DialogContent(
    dialogTitle: String,
    dialogDescription: String,
    storeList: List<StoreListItem>,
    typeface: Typeface?,
    onClickListener: (StoreListItem, Activity?) -> Unit,
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
            item(span = { GridItemSpan(maxLineSpan) }) { DialogHeader(dialogTitle, typeface, dialogDescription) }
            storeList.filter { it.store == Store.DIRECT_URL }.forEach {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    DirectLinkContent(it, onClickListener)
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) { DividerComponent(shouldShowStoresDivider(storeList)) }
            storeList.filter { it.store != Store.DIRECT_URL }.forEach {
                item { StoresListContent(it, onClickListener) }
            }
        }
    }
}

@Composable
private fun StoresListContent(item: StoreListItem, onClickListener: (StoreListItem, Activity?) -> Unit) {
    val context = LocalContext.current.getActivity()
    Box(
        contentAlignment = Center,
        modifier = Modifier.clickable { onClickListener(item, context) },
    ) {
        Column(horizontalAlignment = CenterHorizontally) {
            Image(painter = painterResource(id = item.icon), contentDescription = null)
            Text(
                text = item.title,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun DirectLinkContent(item: StoreListItem, onClickListener: (StoreListItem, Activity?) -> Unit) {
    val context = LocalContext.current.getActivity()
    Text(
        text = item.title,
        textAlign = TextAlign.Center,
        color = Blue,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.clickable { onClickListener(item, context) },
    )
}

@Composable
private fun DialogHeader(
    dialogTitle: String,
    typeface: Typeface?,
    dialogDescription: String,
) {
    Column(horizontalAlignment = CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.appupdater_ic_cloud),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
        )
        Text(
            textAlign = TextAlign.Center,
            text = dialogTitle,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h1,
            fontFamily = typeface?.let { FontFamily(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        )
        Text(
            textAlign = TextAlign.Center,
            text = dialogDescription,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            fontFamily = typeface?.let { FontFamily(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        )
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
