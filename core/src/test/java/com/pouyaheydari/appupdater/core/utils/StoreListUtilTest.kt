package com.pouyaheydari.appupdater.core.utils

import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import org.junit.Assert.assertEquals
import org.junit.Test

internal class StoreListUtilTest {

    @Test
    fun `when passing both direct and app stores, then areDirectAndStoresAvailable return true`() {
        val list = listOf(
            UpdaterStoreList(
                store = Store.DIRECT_URL,
            ),
            UpdaterStoreList(
                store = Store.GOOGLE_PLAY,
            ),
        )

        val result = areDirectAndStoresAvailable(list)

        assertEquals(true, result)
    }

    @Test
    fun `when passing only direct store, then areDirectAndStoresAvailable return false`() {
        val list = listOf(
            UpdaterStoreList(
                store = Store.DIRECT_URL,
            ),
        )

        val result = areDirectAndStoresAvailable(list)

        assertEquals(false, result)
    }

    @Test
    fun `when passing only app stores, then areDirectAndStoresAvailable return false`() {
        val list = listOf(
            UpdaterStoreList(
                store = Store.GOOGLE_PLAY,
            ),
        )

        val result = areDirectAndStoresAvailable(list)

        assertEquals(false, result)
    }

    @Test
    fun `when passing empty list, then areDirectAndStoresAvailable return false`() {
        val list = listOf<UpdaterStoreList>()

        val result = areDirectAndStoresAvailable(list)

        assertEquals(false, result)
    }
}
