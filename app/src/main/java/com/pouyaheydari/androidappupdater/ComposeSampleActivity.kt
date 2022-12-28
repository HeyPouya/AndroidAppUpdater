package com.pouyaheydari.androidappupdater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pouyaheydari.androidappupdater.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.compose.AndroidAppUpdater
import com.pouyaheydari.appupdater.compose.R
import com.pouyaheydari.appupdater.compose.utils.storeList
import com.pouyaheydari.appupdater.core.pojo.Theme

class ComposeSampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppUpdaterTheme {
                AndroidAppUpdater(
                    dialogTitle = stringResource(id = R.string.appupdater_app_name),
                    dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
                    storeList = storeList,
                    theme = Theme.DARK,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            dialogTitle = stringResource(id = R.string.appupdater_app_name),
            dialogDescription = stringResource(id = R.string.appupdater_download_notification_desc),
            storeList = storeList,
            theme = Theme.DARK,
        )
    }
}
