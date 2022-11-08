package ir.heydarii.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import ir.heydarii.appupdater.utils.store
import ir.heydarii.appupdater.utils.updateDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "showUpdateDialogTag"
const val FONT_PATH = "fonts/vazir.ttf"
const val customUrl = "https://cafebazaar.ir/download/bazaar.apk"

/**
 * Main activity of the sample application
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnKotlin.setOnClickListener {
            kotlinSample()
        }

        btnDSL.setOnClickListener {
            dslSample()
        }
    }

    private fun dslSample() {
        updateDialogBuilder {
            title = getString(R.string.library_title)
            description = getString(R.string.library_description)
            isForceUpdate = false
            typeface = Typeface.createFromAsset(assets, FONT_PATH)
            list = listOf(
                store {
                    store = Store.DIRECT_URL
                    title = getString(R.string.direct_download, "1")
                    icon = R.mipmap.ic_launcher
                    url = customUrl
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.DIRECT_URL
                    title = getString(R.string.direct_download, "2")
                    icon = R.mipmap.ic_launcher
                    url = customUrl
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.GOOGLE_PLAY
                    title = getString(R.string.play)
                    icon = R.drawable.appupdater_ic_google_play
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.CAFE_BAZAAR
                    title = getString(R.string.bazaar)
                    icon = R.drawable.appupdater_ic_bazar
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.MYKET
                    title = getString(R.string.myket)
                    icon = R.drawable.appupdater_ic_myket
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.IRAN_APPS
                    title = getString(R.string.iran_apps)
                    icon = R.drawable.appupdater_ic_iran_apps
                    packageName = BuildConfig.APPLICATION_ID
                }
            )
        }.show(supportFragmentManager, TAG)
    }

    private fun kotlinSample() {
        //   make a list of stores
        val list = ArrayList<UpdaterStoreList>()

        // typeface to use in dialog
        val font = Typeface.createFromAsset(assets, FONT_PATH)

        // direct download
        list.add(
            UpdaterStoreList(
                Store.DIRECT_URL,
                getString(R.string.direct_download, "1"),
                R.mipmap.ic_launcher,
                customUrl,
                BuildConfig.APPLICATION_ID
            )
        )
        list.add(
            UpdaterStoreList(
                Store.DIRECT_URL,
                getString(R.string.direct_download, "2"),
                R.mipmap.ic_launcher,
                customUrl,
                BuildConfig.APPLICATION_ID
            )
        )

        // stores
        list.add(
            UpdaterStoreList(
                Store.GOOGLE_PLAY,
                getString(R.string.play),
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_google_play
            )
        )
        list.add(
            UpdaterStoreList(
                Store.CAFE_BAZAAR,
                getString(R.string.bazaar),
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_bazar
            )
        )
        list.add(
            UpdaterStoreList(
                Store.MYKET,
                getString(R.string.myket),
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_myket
            )
        )
        list.add(
            UpdaterStoreList(
                Store.IRAN_APPS,
                getString(R.string.iran_apps),
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_iran_apps
            )
        )

        // creating update dialog
        AppUpdaterDialog.getInstance(
            getString(R.string.library_title),
            getString(R.string.library_description),
            list,
            false,
            font
        ).show(supportFragmentManager, TAG)
    }
}
