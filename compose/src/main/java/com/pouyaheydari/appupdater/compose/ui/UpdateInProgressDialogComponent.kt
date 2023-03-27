package com.pouyaheydari.appupdater.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pouyaheydari.appupdater.compose.R

@Composable
fun UpdateInProgressDialogComponent() {
    Dialog(onDismissRequest = { /* Do nothing */ }) {
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
                    modifier = Modifier.padding(horizontal = 8.dp),
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
private fun UpdateInProgressDialogPreview() {
    UpdateInProgressDialogComponent()
}
