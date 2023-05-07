package com.pouyaheydari.androidappupdater.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pouyaheydari.androidappupdater.R
import com.pouyaheydari.androidappupdater.ui.compose.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.androidappupdater.utils.getNormalList
import com.pouyaheydari.appupdater.compose.AndroidAppUpdater
import com.pouyaheydari.appupdater.compose.utils.storeList
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.R as coreR

/**
 * To use the library in compose,
 * you only need to add dependencies to:
 * core and compose
 */
class ComposeSampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppUpdaterTheme {
                var state by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { state = true }) {
                        Text(text = getString(R.string.show_compose_dialog))
                    }
                }
                if (state) {
                    AndroidAppUpdater(
                        dialogTitle = stringResource(id = coreR.string.appupdater_app_name),
                        dialogDescription = stringResource(id = R.string.library_description),
                        storeList = getNormalList(this),
                        theme = Theme.DARK,
                        onDismissRequested = { state = false },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            dialogTitle = stringResource(id = coreR.string.appupdater_app_name),
            dialogDescription = stringResource(id = R.string.library_description),
            storeList = storeList,
            theme = Theme.DARK,
        )
    }
}
