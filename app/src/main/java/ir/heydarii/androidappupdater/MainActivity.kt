package ir.heydarii.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList
import ir.heydarii.appupdater.pojo.store
import ir.heydarii.appupdater.pojo.updateDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "showUpdateDialogTag"
const val FONT_PATH = "fonts/iran_sans_mobile.ttf"

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
            title = "New Update !"
            description = "Lots of new features! Update right now"
            isForceUpdate = false
            typeface = Typeface.createFromAsset(assets, FONT_PATH)
            list = listOf(
                store {
                    store = Store.DIRECT_URL
                    title = "Direct Download 1"
                    icon = R.mipmap.ic_launcher
                    url = "https://cafebazaar.ir/download/bazaar.apk"
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.DIRECT_URL
                    title = "Direct Download 2"
                    icon = R.mipmap.ic_launcher
                    url = "https://cafebazaar.ir/download/bazaar.apk"
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.GOOGLE_PLAY
                    title = "Play"
                    icon = R.drawable.appupdater_ic_google_play
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.CAFE_BAZAAR
                    title = "Bazaar"
                    icon = R.drawable.appupdater_ic_bazar
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.MYKET
                    title = "Myket"
                    icon = R.drawable.appupdater_ic_myket
                    packageName = BuildConfig.APPLICATION_ID
                },
                store {
                    store = Store.IRAN_APPS
                    title = "Iran apps"
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
                "Direct Download 1",
                R.mipmap.ic_launcher,
                "https://cafebazaar.ir/download/bazaar.apk",
                BuildConfig.APPLICATION_ID
            )
        )
        list.add(
            UpdaterStoreList(
                Store.DIRECT_URL,
                "Direct Download 2",
                R.mipmap.ic_launcher,
                "https://cafebazaar.ir/download/bazaar.apk",
                BuildConfig.APPLICATION_ID
            )
        )

        // stores
        list.add(
            UpdaterStoreList(
                Store.GOOGLE_PLAY,
                "Play",
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_google_play
            )
        )
        list.add(
            UpdaterStoreList(
                Store.CAFE_BAZAAR,
                "Bazaar",
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_bazar
            )
        )
        list.add(
            UpdaterStoreList(
                Store.MYKET,
                "Myket",
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_myket
            )
        )
        list.add(
            UpdaterStoreList(
                Store.IRAN_APPS,
                "Iran apps",
                packageName = BuildConfig.APPLICATION_ID,
                icon = R.drawable.appupdater_ic_iran_apps
            )
        )

        // creating update dialog
        AppUpdaterDialog.getInstance(
            "New Update !",
            "Lots of new features! Update right now",
            list,
            true,
            font
        ).show(supportFragmentManager, TAG)

    }
}