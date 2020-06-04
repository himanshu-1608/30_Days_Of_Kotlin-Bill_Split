package com.himanshu.billsplit.database.attachs

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class DBAsyncTaskAttach(private val context: Context, private val attachEntity: AttachEntity) : AsyncTask<Void, Void, Boolean>() {

    private val db = Room
        .databaseBuilder(context, AttachDatabase::class.java,"Attaches")
        .build()

    override fun doInBackground(vararg params: Void?): Boolean {
        db.attachDao().insertAttach(attachEntity)
        db.close()
        return true
    }

}