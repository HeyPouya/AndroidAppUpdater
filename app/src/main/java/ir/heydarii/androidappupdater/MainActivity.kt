package ir.heydarii.androidappupdater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<UpdaterStoreList>()
        list.add(UpdaterStoreList(Store.DIRECT_URL, "دانلود مستقیم", url= "https://cafebazaar.ir/download/bazaar.apk", packageName ="Salam"))
        list.add(UpdaterStoreList(Store.GOOGLE_PLAY, "گوگل پلی", packageName =  "Salam"))
        list.add(UpdaterStoreList(Store.CAFE_BAZAAR, "کافه بازار",packageName ="Salam"))
        list.add(UpdaterStoreList(Store.MYKET, "مایکت", packageName = "Salam"))
        list.add(UpdaterStoreList(Store.IRAN_APPS, "ایران اپس", packageName = "Salam"))
        AppUpdaterDialog.getInstance("آپدیت جدید!", "کلی فیچر های جدید به اپ اضافه شده", list, true).show(supportFragmentManager, "tagz")


    }
}
