package ir.heydarii.androidappupdater

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

const val TAG = "showUpdateDialogTag"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //typeface to use in dialog
        val font = Typeface.createFromAsset(assets, "fonts/iran_sans_mobile.ttf")

        //make a list of stores
        val list = ArrayList<UpdaterStoreList>()

        //direct download
        list.add(UpdaterStoreList(Store.DIRECT_URL, "Direct Download",R.mipmap.ic_launcher , "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))

        //stores
        list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "Google Play", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.CAFE_BAZAAR, "Cafe Bazaar", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.MYKET, "MyKet", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.IRAN_APPS, "Iran Apps", packageName = BuildConfig.APPLICATION_ID))


        //creating update dialog
        AppUpdaterDialog.getInstance("New Update!!!!", "Lots of new features!! upgrade yo the new version.", list, true, font).show(supportFragmentManager, "")


    }
}
