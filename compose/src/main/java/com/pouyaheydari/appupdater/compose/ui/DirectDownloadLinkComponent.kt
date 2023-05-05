package com.pouyaheydari.appupdater.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pouyaheydari.appupdater.compose.ui.theme.Blue
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

@Composable
fun DirectDownloadLinkComponent(item: StoreListItem, onClickListener: (StoreListItem) -> Unit = {}) {
    Text(
        text = item.title,
        textAlign = TextAlign.Center,
        color = Blue,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.clickable { onClickListener(item) },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    DirectDownloadLinkComponent(
        item = StoreListItem(title = "Direct Link 1"),
    )
}
