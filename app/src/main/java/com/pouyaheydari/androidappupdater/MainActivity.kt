package com.pouyaheydari.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pouyaheydari.appupdater.AppUpdaterDialog
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import com.pouyaheydari.appupdater.dsl.store
import com.pouyaheydari.appupdater.dsl.updateDialogBuilder

const val TAG = "showUpdateDialogTag"
const val FONT_PATH = "fonts/vazir.ttf"
const val CUSTOM_URL = "https://cafebazaar.ir/download/bazaar.apk"
const val SAMPLE_PACKAGE_NAME = "com.tencent.mm"

/**
 * Main activity of the sample application
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnKotlin).setOnClickListener {
            kotlinSample()
        }

        findViewById<Button>(R.id.btnDSL).setOnClickListener {
            dslSample()
        }
    }

    /**
     * To use the library like below,
     * you only need to add dependencies to:
     * core and dsl
     */
    private fun dslSample() {
        updateDialogBuilder {
            title = getString(R.string.library_title)
            description = getString(R.string.library_description)
            isForceUpdate = false
            typeface = Typeface.createFromAsset(assets, FONT_PATH)
            theme = Theme.DARK
            list = listOf(
                store {
                    store = Store.DIRECT_URL
                    title = getString(R.string.direct_download, "1")
                    icon = R.mipmap.ic_launcher
                    url = CUSTOM_URL
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.DIRECT_URL
                    title = getString(R.string.direct_download, "2")
                    icon = R.mipmap.ic_launcher
                    url = CUSTOM_URL
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.GOOGLE_PLAY
                    title = getString(R.string.play)
                    icon = R.drawable.appupdater_ic_google_play
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.CAFE_BAZAAR
                    title = getString(R.string.bazaar)
                    icon = R.drawable.appupdater_ic_bazar
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.MYKET
                    title = getString(R.string.myket)
                    icon = R.drawable.appupdater_ic_myket
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.IRAN_APPS
                    title = getString(R.string.iran_apps)
                    icon = R.drawable.appupdater_ic_iran_apps
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.HUAWEI_APP_GALLERY
                    title = getString(R.string.app_gallery)
                    icon = R.drawable.appupdater_ic_app_gallery
                    packageName = SAMPLE_PACKAGE_NAME
                },
                store {
                    store = Store.SAMSUNG_GALAXY_STORE
                    title = getString(R.string.galaxy_store)
                    icon = R.drawable.appupdater_ic_galaxy_store
                    packageName = SAMPLE_PACKAGE_NAME
                }
            )
        }.show(supportFragmentManager, TAG)
    }

    /**
     * To use the library like below,
     * you only need to add dependencies to:
     * core and appupdater
     */
    private fun kotlinSample() {
        // typeface to use in dialog
        val font = Typeface.createFromAsset(assets, FONT_PATH)

        //   make a list of stores
        val list = listOf(
            // direct download
            UpdaterStoreList(
                store = Store.DIRECT_URL,
                title = getString(R.string.direct_download, "1"),
                icon = R.mipmap.ic_launcher,
                url = CUSTOM_URL,
                packageName = SAMPLE_PACKAGE_NAME
            ),
            UpdaterStoreList(
                Store.DIRECT_URL,
                getString(R.string.direct_download, "2"),
                R.mipmap.ic_launcher,
                CUSTOM_URL,
                SAMPLE_PACKAGE_NAME
            ),
            // stores
            UpdaterStoreList(
                Store.GOOGLE_PLAY,
                getString(R.string.play),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_google_play
            ),
            UpdaterStoreList(
                Store.CAFE_BAZAAR,
                getString(R.string.bazaar),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_bazar
            ),
            UpdaterStoreList(
                Store.MYKET,
                getString(R.string.myket),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_myket
            ),
            UpdaterStoreList(
                Store.IRAN_APPS,
                getString(R.string.iran_apps),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_iran_apps
            ),
            UpdaterStoreList(
                Store.HUAWEI_APP_GALLERY,
                getString(R.string.app_gallery),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_app_gallery
            ),
            UpdaterStoreList(
                Store.SAMSUNG_GALAXY_STORE,
                getString(R.string.galaxy_store),
                packageName = SAMPLE_PACKAGE_NAME,
                icon = R.drawable.appupdater_ic_galaxy_store
            )
        )
        // creating update dialog
        AppUpdaterDialog.getInstance(
            title = getString(R.string.library_title),
            description = getString(R.string.library_description),
            storeList = list,
            isForce = false,
            typeface = font,
            theme = Theme.LIGHT
        ).show(supportFragmentManager, TAG)
    }
}
