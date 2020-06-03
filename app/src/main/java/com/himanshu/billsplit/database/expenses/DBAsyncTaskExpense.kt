package com.himanshu.billsplit.database.expenses

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

class DBAsyncTaskExpense(private val context: Context, private val expenseEntity: ExpenseEntity) : AsyncTask<Void, Void, Boolean>() {

    private val db = Room
        .databaseBuilder(context, ExpenseDatabase::class.java,"Expenses")
        .build()

    override fun doInBackground(vararg params: Void?): Boolean {
        db.expenseDao().insertExpense(expenseEntity)
        db.close()
        return true
    }

}