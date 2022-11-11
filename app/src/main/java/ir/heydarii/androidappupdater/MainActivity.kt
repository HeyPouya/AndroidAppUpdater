package ir.heydarii.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

private const val TAG = "showUpdateDialogTag"
private const val FONT_PATH = "fonts/vazir.ttf"
private const val CUSTOM_URL = "https://cafebazaar.ir/download/bazaar.apk"

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
            Toast.makeText(this, "Change to kotlinDSL build variant to see this option", Toast.LENGTH_LONG).show()
        }
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
                CUSTOM_URL,
                BuildConfig.APPLICATION_ID
            )
        )
        list.add(
            UpdaterStoreList(
                Store.DIRECT_URL,
                getString(R.string.direct_download, "2"),
                R.mipmap.ic_launcher,
                CUSTOM_URL,
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
