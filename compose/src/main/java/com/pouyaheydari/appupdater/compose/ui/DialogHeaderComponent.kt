package com.pouyaheydari.appupdater.compose.ui

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.appupdater.compose.R

@Composable
fun DialogHeaderComponent(
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

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {
    DialogHeaderComponent(
        dialogTitle = stringResource(id = R.string.appupdater_app_name),
        typeface = null,
        dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
    )
}
