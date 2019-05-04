package ir.heydarii.appupdater.pojomodel

import android.os.Parcelable
import ir.heydarii.appupdater.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdaterFragmentModel(
    val title: String?,
    val description: String?,
    val list: List<UpdaterStoreList>,
    val isForceUpdate: Boolean
) : Parcelable

@Parcelize
data class UpdaterStoreList(
    val store: Store = Store.DIRECT_URL,
    val title: String = "Store",
    val icon: Int = R.drawable.appupdater_ic_cloud,
    val url: String = "",
    val packageName: String = ""
) : Parcelable