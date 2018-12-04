package ir.heydarii.appupdater.pojomodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UpdaterFragmentModel(
    val title: String,
    val description: String,
    val list: List<UpdaterStoreList>,
    val isForceUpdate: Boolean
) :
    Parcelable

@Parcelize
data class UpdaterStoreList(
    val store: Store,
    val title: String,
    val icon: Int,
    val url: String,
    val packageName: String
) : Parcelable