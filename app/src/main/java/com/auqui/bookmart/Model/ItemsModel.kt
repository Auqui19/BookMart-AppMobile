package com.auqui.bookmart.Model

import android.os.Parcel
import android.os.Parcelable

data class ItemsModel (
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var genre: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var author: String = "",
    var numberInCart: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() ?: ArrayList(),
        parcel.createStringArrayList() ?: ArrayList(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(picUrl)
        parcel.writeStringList(genre)
        parcel.writeDouble(price)
        parcel.writeDouble(rating)
        parcel.writeString(author)
        parcel.writeInt(numberInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}