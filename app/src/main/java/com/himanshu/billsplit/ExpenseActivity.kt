package com.himanshu.billsplit

import android.content.Intent
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
        if(friendsList.size==0) {
            binding.txtDef.visibility = View.VISIBLE
            binding.recyclerList.visibility = View.GONE
            binding.btnSplit.visibility = View.GONE
            binding.txtCheck.visibility = View.GONE
            binding.layoutBill.visibility = View.GONE
        } else {
            binding.txtDef.visibility = View.GONE
            binding.recyclerList.visibility = View.VISIBLE
            binding.btnSplit.visibility = View.VISIBLE
            binding.txtCheck.visibility = View.VISIBLE
            binding.layoutBill.visibility = View.VISIBLE
        }
        recyclerAdapter = FriendListAdapter(this@ExpenseActivity,friendsList)
        binding.recyclerList.layoutManager = layoutManager
        binding.recyclerList.adapter = recyclerAdapter

        binding.btnSplit.setOnClickListener {
            val intent = Intent(this@ExpenseActivity,FinalActivity::class.java)
            intent.putExtra("ListOfFriends",recyclerAdapter.checkList)
            startActivity(intent)
        }
    }
}
