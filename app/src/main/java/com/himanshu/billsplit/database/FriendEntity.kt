package com.himanshu.billsplit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friends")
data class FriendEntity(
    @PrimaryKey val friend_name: String,
    @ColumnInfo(name = "debt") val debt: Float
)
