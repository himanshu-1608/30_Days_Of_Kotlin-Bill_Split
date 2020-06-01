package com.himanshu.billsplit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.billsplit.adapters.FriendListAdapter
import com.himanshu.billsplit.database.GetFriends
import com.himanshu.billsplit.databinding.ActivityExpenseBinding

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ExpenseActivity,R.layout.activity_expense)
        supportActionBar?.hide()
        layoutManager = LinearLayoutManager(this@ExpenseActivity)
        val friendsList = GetFriends(applicationContext).execute().get()
        binding.txtDef.visibility = if(friendsList.size==0) View.VISIBLE else View.GONE
        binding.recyclerList.visibility = if(friendsList.size!=0) View.VISIBLE else View.GONE
        recyclerAdapter = FriendListAdapter(this@ExpenseActivity,friendsList)
        binding.recyclerList.layoutManager = layoutManager
        binding.recyclerList.adapter = recyclerAdapter
    }
}
