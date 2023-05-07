package com.pouyaheydari.appupdater.core.stores

import android.net.Uri
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class NineAppsTest {
    @get:Rule
    val intentsTestRule = IntentsRule()

    @Test
    fun whenCalling_setStoreData_then_intentGetsFiredCorrectly() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val nineApps = NineApps()
        val packageName = appContext.packageName
        val url = "https://pouyaheydari.com"

        nineApps.setStoreData(StoreListItem(store = Store.NINE_APPS_STORE, packageName = packageName, url = url))
        nineApps.showStore(appContext)

        Intents.intended(IntentMatchers.hasPackage(NINE_APPS_PACKAGE))
        Intents.intended(IntentMatchers.hasData(Uri.parse("$NINE_APPS_STORE_URL$packageName")))
    }
}
