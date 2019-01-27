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
        list.add(UpdaterStoreList(Store.DIRECT_URL, "دانلود مستقیم", R.mipmap.ic_launcher, "https://cafebazaar.ir/download/bazaar.apk", BuildConfig.APPLICATION_ID))

        //stores
        list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "گوگل پلی", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.CAFE_BAZAAR, "کافه بازار", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.MYKET, "مایکت", packageName = BuildConfig.APPLICATION_ID))
        list.add(UpdaterStoreList(Store.IRAN_APPS, "ایران اپس", packageName = BuildConfig.APPLICATION_ID))


        //creating update dialog
        AppUpdaterDialog.getInstance("آپدیت جدید", "یه عالمه فیچرهای جدید تو اپمون گذاشتیم. اپت رو آپدیت کن!", list, true, font).show(supportFragmentManager, TAG)


    }
}
