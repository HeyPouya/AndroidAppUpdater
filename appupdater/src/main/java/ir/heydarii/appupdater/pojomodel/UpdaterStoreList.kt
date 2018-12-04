package ir.heydarii.appupdater.pojomodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class UpdaterFragmentModel(val title: String, val description: String, val list: List<UpdaterStoreList>, val isForceUpdate: Boolean) :
    Parcelable

@Parcelize
data class UpdaterStoreList(val title: String, val icon: Int, val url: String, val packageName: String) : Parcelable