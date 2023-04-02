package com.pouyaheydari.appupdater.compose.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

@Composable
fun SquareStoreItemComponent(item: StoreListItem, onClickListener: (StoreListItem, Activity?) -> Unit, activity: Activity?) {
    Column(
        modifier = Modifier.clickable { onClickListener(item, activity) },
        horizontalAlignment = CenterHorizontally,
    ) {
        Image(painter = painterResource(id = item.icon), contentDescription = null)
        Text(
            text = item.title,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SquareStoreItemComponent(item = StoreListItem(), onClickListener = { _, _ -> }, activity = null)
}
