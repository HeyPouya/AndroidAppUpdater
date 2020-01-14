package ir.heydarii.appupdater.pojo

import android.os.Parcelable
import ir.heydarii.appupdater.R
import kotlinx.android.parcel.Parcelize

/**
 * User has to pass this model to the library
 */
@Parcelize
data class UpdaterFragmentModel(
    val title: String?,
    val description: String?,
    val list: List<UpdaterStoreList>,
    val isForceUpdate: Boolean
) : Parcelable

/**
 * The model that we are using for list of stores
 */
@Parcelize
data class UpdaterStoreList(
    val store: Store = Store.DIRECT_URL,
    val title: String = "Store",
    val icon: Int = R.drawable.appupdater_ic_cloud,
    val url: String = "",
    val packageName: String = ""
) : Parcelable