package com.pouyaheydari.appupdater.compose.ui.components

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.pouyaheydari.appupdater.compose.ui.models.DialogHeaderModel
import com.pouyaheydari.appupdater.compose.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.directdownload.R as directDownloadR

/**
 * Shows header icon, title and description
 *
 * @param content DialogHeaderComponentModel
 */
@Composable
internal fun DialogHeaderComponent(
    modifier: Modifier = Modifier,
    content: DialogHeaderModel = DialogHeaderModel(),
    typeface: Typeface? = null,
) {
    Column(modifier = modifier, horizontalAlignment = CenterHorizontally) {
        Image(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .testTag(content.dialogIcon.toString()),
            painter = painterResource(id = content.dialogIcon),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            textAlign = TextAlign.Center,
            text = content.dialogTitle,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = typeface?.let { FontFamily(it) },
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            textAlign = TextAlign.Center,
            text = content.dialogDescription,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = typeface?.let { FontFamily(it) },
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun Preview() {
    AndroidAppUpdaterTheme {
        DialogHeaderComponent(
            content = DialogHeaderModel(
                dialogTitle = stringResource(id = directDownloadR.string.appupdater_app_name),
                dialogDescription = stringResource(id = directDownloadR.string.appupdater_download_notification_desc),
            ),
        )
    }
}
