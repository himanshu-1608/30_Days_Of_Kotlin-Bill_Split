package com.himanshu.billsplit.database

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friends")
data class FriendEntity(
    @PrimaryKey val friend_name: String,
    @ColumnInfo(name = "debt") val debt: Float
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(friend_name)
        parcel.writeFloat(debt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendEntity> {
        override fun createFromParcel(parcel: Parcel): FriendEntity {
            return FriendEntity(parcel)
        }

        override fun newArray(size: Int): Array<FriendEntity?> {
            return arrayOfNulls(size)
        }
    }

}
