package ir.heydarii.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

const val TAG = "showUpdateDialogTag"
const val FONT_PATH = "fonts/iran_sans_mobile.ttf"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //typeface to use in dialog
        val font = Typeface.createFromAsset(assets, FONT_PATH)

        //make a list of stores
        val list = ArrayList<UpdaterStoreList>()

        //direct download
        list.add(UpdaterStoreList(Store.DIRECT_URL, "Direct Download 1", R.mipmap.ic_launcher, "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.DIRECT_URL, "Direct Download 2", R.mipmap.ic_launcher, "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))


        //stores
        list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "Play", packageName = BuildConfig.APPLICATION_ID,icon = R.drawable.appupdater_ic_google_play))
        list.add(UpdaterStoreList(Store.CAFE_BAZAAR, "Bazaar", packageName = BuildConfig.APPLICATION_ID,icon = R.drawable.appupdater_ic_bazar))
        list.add(UpdaterStoreList(Store.MYKET, "Myket", packageName = BuildConfig.APPLICATION_ID,icon = R.drawable.appupdater_ic_myket))
        list.add(UpdaterStoreList(Store.IRAN_APPS, "Iran apps", packageName = BuildConfig.APPLICATION_ID,icon = R.drawable.appupdater_ic_iran_apps))


        //creating update dialog
        AppUpdaterDialog.getInstance("New Update !", "Lots of new features! Update right now", list, true, font).show(supportFragmentManager, TAG)


    }
}
