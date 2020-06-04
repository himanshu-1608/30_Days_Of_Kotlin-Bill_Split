package com.himanshu.billsplit.database.friends

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class DBAsyncTaskFriend(private val context: Context, private val friendEntity: FriendEntity, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {

    // Mode 1 -> Checker by Name
    // Mode 2 -> Add
    // Mode 3 -> Update

    private val db = Room.databaseBuilder(context, FriendDatabase::class.java,"Friends").build()

    override fun doInBackground(vararg params: Void?): Boolean {
        when(mode) {
            1 -> {
                //Checker Name
                val friend : FriendEntity? = db.friendDao().getFriendByName(friendEntity.friend_name)
                db.close()
                return friend != null
            }
            2 -> {
                //Add
                db.friendDao().insertFriend(friendEntity)
                db.close()
                return true
            }
            3 -> {
                //Update
                db.friendDao().updateFriend(friendEntity)
                return true
            }
        }
        return false
    }

}