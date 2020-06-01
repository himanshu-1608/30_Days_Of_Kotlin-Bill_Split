package com.himanshu.billsplit.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FriendEntity::class],version = 1,exportSchema = false)
abstract class FriendDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
}