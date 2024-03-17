package com.example.laba4mobiledop

import android.os.Parcel
import android.os.Parcelable

data class Settings(
    var numberOfRounds: Int,
    var numberOfTraps: Int,
    var numberOfChests: Int
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numberOfRounds)
        parcel.writeInt(numberOfTraps)
        parcel.writeInt(numberOfChests)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Settings> {
        override fun createFromParcel(parcel: Parcel): Settings {
            return Settings(parcel)
        }

        override fun newArray(size: Int): Array<Settings?> {
            return arrayOfNulls(size)
        }
    }
}
