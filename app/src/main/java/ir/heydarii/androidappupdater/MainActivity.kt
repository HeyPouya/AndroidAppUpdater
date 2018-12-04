package ir.heydarii.androidappupdater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.heydarii.appupdater.AppUpdaterDialog
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val list = ArrayList<UpdaterStoreList>()
        AppUpdaterDialog.getInstance("Please Update", "This is the description", list, true).show(supportFragmentManager, "tagz")


    }
}
