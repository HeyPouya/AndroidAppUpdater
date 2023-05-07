package com.pouyaheydari.appupdater.compose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.appupdater.core.R as coreR

@Composable
fun DividerComponent() {
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
            text = stringResource(id = coreR.string.appupdater_or),
        )
        Divider(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .weight(1F)
                .padding(start = 8.dp, end = 16.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DividerComponent()
}
