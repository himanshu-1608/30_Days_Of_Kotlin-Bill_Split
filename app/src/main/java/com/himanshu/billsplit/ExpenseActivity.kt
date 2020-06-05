package com.himanshu.billsplit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.billsplit.adapters.FriendListAdapter
import com.himanshu.billsplit.database.friends.GetFriends
import com.himanshu.billsplit.databinding.ActivityExpenseBinding

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ExpenseActivity,R.layout.activity_expense)
        layoutManager = LinearLayoutManager(this@ExpenseActivity)
        supportActionBar?.title = "Add Expense"
        val friendsList = GetFriends(applicationContext).execute().get()

        recyclerAdapter = FriendListAdapter(this@ExpenseActivity,friendsList)
        binding.recyclerList.layoutManager = layoutManager
        binding.recyclerList.adapter = recyclerAdapter
        binding.btnSplit.setOnClickListener {
            if(checker(binding.etCost.text.toString().trim())) {
                val intent = Intent(this@ExpenseActivity, FinalActivity::class.java)
                intent.putExtra("ListOfFriends", recyclerAdapter.checkList)
                intent.putExtra("TotalCost", binding.etCost.text.toString().trim().toDouble())
                startActivity(intent)
            }
        }
    }

    private fun checker(finalString: String): Boolean {
        return if(finalString.isEmpty()) {
            binding.etCost.error = "Required"
            binding.etCost.requestFocus()
            false
        } else if(finalString.toDouble()==0.00) {
            binding.etCost.error = "Enter valid cost"
            binding.etCost.requestFocus()
            false
        } else true
    }

}
