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
        list.add(UpdaterStoreList(Store.CAFE_BAZAAR, "کافه بازار", R.mipmap.ic_launcher, "", "Salam"))
        AppUpdaterDialog.getInstance("آپدیت جدید!", "کلی فیچر های جدید به اپ اضافه شده", list, true).show(supportFragmentManager, "tagz")


    }
}
