package com.pouyaheydari.androidappupdater

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pouyaheydari.androidappupdater.utils.getDSLList
import com.pouyaheydari.androidappupdater.utils.getNormalList
import com.pouyaheydari.appupdater.AppUpdaterDialog
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.dsl.updateDialogBuilder

private const val TAG = "showUpdateDialogTag"
private const val FONT_PATH = "fonts/vazir.ttf"

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

        findViewById<Button>(R.id.btnCompose).setOnClickListener {
            startActivity(Intent(this, ComposeSampleActivity::class.java))
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
            list = getDSLList(this@MainActivity)
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
        val list = getNormalList(this)
        // creating update dialog
        AppUpdaterDialog.getInstance(
            title = getString(R.string.library_title),
            description = getString(R.string.library_description),
            storeList = list,
            isForce = false,
            typeface = font,
            theme = Theme.LIGHT,
        ).show(supportFragmentManager, TAG)
    }
}
