package com.pouyaheydari.appupdater.compose

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pouyaheydari.appupdater.compose.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.compose.ui.theme.Blue
import com.pouyaheydari.appupdater.compose.utils.getActivity
import com.pouyaheydari.appupdater.compose.utils.storeList
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.core.utils.areDirectAndStoresAvailable

@Composable
fun AndroidAppUpdater(
    dialogTitle: String = "",
    dialogDescription: String = "",
    storeList: List<UpdaterStoreList> = listOf(),
    isForce: Boolean = false,
    typeface: Typeface? = null,
    theme: Theme = Theme.LIGHT,
) {
    val viewModel: AndroidAppUpdaterViewModel = viewModel()

    AndroidAppUpdaterTheme(darkTheme = theme == Theme.DARK) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = isForce,
                dismissOnClickOutside = isForce,
            ),
        ) {
            DialogContent(dialogTitle, dialogDescription, storeList, typeface, viewModel)
        }
        if (viewModel.state.value) {
            UpdateInProgressDialog()
        }
    }
}

@Composable
fun DialogContent(
    dialogTitle: String,
    dialogDescription: String,
    storeList: List<UpdaterStoreList>,
    typeface: Typeface?,
    viewModel: AndroidAppUpdaterViewModel,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            DialogHeader(dialogTitle, typeface, dialogDescription)
            DirectLinkContent(storeList, viewModel)
            DividerContent(areDirectAndStoresAvailable(storeList))
            StoresListContent(storeList, viewModel)
        }
    }
}

@Composable
private fun StoresListContent(storeList: List<UpdaterStoreList>, viewModel: AndroidAppUpdaterViewModel) {
    val context = LocalContext.current.getActivity()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            storeList.filter { it.store != Store.DIRECT_URL }.forEach {
                item {
                    Box(
                        contentAlignment = Center,
                        modifier = Modifier.clickable { viewModel.onListListener(it, context) },
                    ) {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Image(painter = painterResource(id = it.icon), contentDescription = null)
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                        }
                    }
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(vertical = 32.dp),
    )
}

@Composable
private fun DividerContent(shouldShow: Boolean) {
    if (shouldShow) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Divider(
                color = MaterialTheme.colors.background,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 16.dp, end = 8.dp),
            )
            Text(
                text = stringResource(id = R.string.appupdater_or),
            )
            Divider(
                color = MaterialTheme.colors.background,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp, end = 16.dp),
            )
        }
    }
}

@Composable
private fun DirectLinkContent(storeList: List<UpdaterStoreList>, viewModel: AndroidAppUpdaterViewModel) {
    val context = LocalContext.current.getActivity()
    LazyColumn(
        content = {
            storeList.filter { it.store == Store.DIRECT_URL }.forEach {
                item {
                    Text(
                        text = it.title,
                        textAlign = TextAlign.Center,
                        color = Blue,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.clickable { viewModel.onListListener(it, context) },
                    )
                }
            }
        },
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false,
        modifier = Modifier.padding(top = 32.dp, start = 8.dp, end = 8.dp),
        contentPadding = PaddingValues(top = 16.dp),
    )
}

@Composable
private fun DialogHeader(
    dialogTitle: String,
    typeface: Typeface?,
    dialogDescription: String,
) {
    Image(
        painter = painterResource(id = R.drawable.appupdater_ic_cloud),
        contentDescription = null,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(top = 16.dp),
    )
    Text(
        textAlign = TextAlign.Center,
        text = dialogTitle,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h1,
        fontFamily = typeface?.let { FontFamily(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    )
    Text(
        textAlign = TextAlign.Center,
        text = dialogDescription,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body2,
        fontFamily = typeface?.let { FontFamily(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    )
}

@Composable
private fun UpdateInProgressDialog() {
    Dialog(onDismissRequest = { }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
            ) {
                Text(
                    text = stringResource(id = (R.string.appupdater_please_wait)),
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(all = 8.dp),
                )
                Text(
                    text = stringResource(id = (R.string.appupdater_downloading_new_version)),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(all = 8.dp),
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                )
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

@Preview(showBackground = true)
@Composable
fun UpdateInProgressDialogPreview() {
    UpdateInProgressDialog()
}
